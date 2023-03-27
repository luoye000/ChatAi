package com.luoye.chat.openai;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import lombok.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import com.luoye.apptool.utils.LogUtils;
import com.luoye.chat.info.LinkEorr;
import com.luoye.dblibrary.db.Message;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user: ChatGPT链接类
 */


public class ChatLink {

    private static final String TAG = "---ChatLink";


    private static final int TIMEOUT = 300;// 秒


    private String url = "https://api.openai.com/v1/chat/completions";


    public void link(AiChatCompletion aiChatCompletion, ChatSourceListener chatSourceListener) {
        try {
            String requestBody = new ObjectMapper().writeValueAsString(aiChatCompletion);
            LogUtils.i(TAG, "requestBody: " + requestBody);
            link(requestBody, chatSourceListener);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            chatSourceListener.onThrowable(e, LinkEorr.JSON);
            chatSourceListener.onClosed(null);
        }
    }


    private Message message;

    /**
     * 流式输出
     */
    private void link(String requestBody, ChatSourceListener chatSourceListener) {
        LogUtils.i(TAG, "link key:" + ("".equals(ChatLinkInfo.KeyUser) ? ChatLinkInfo.KeyLocalhost : ChatLinkInfo.KeyUser));
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(ChatLinkInfo.TIMEOUT, TimeUnit.SECONDS);
        client.writeTimeout(ChatLinkInfo.TIMEOUT, TimeUnit.SECONDS);
        client.readTimeout(ChatLinkInfo.TIMEOUT, TimeUnit.SECONDS);
        EventSource.Factory factory = EventSources.createFactory(client.build());
        //  MediaType mediaType = MediaType.Companion.parse("application/json");
        //  RequestBody stringBody = RequestBody.Companion.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + ("".equals(ChatLinkInfo.KeyUser) ? ChatLinkInfo.KeyLocalhost : ChatLinkInfo.KeyUser))
                .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
                .build();
        factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onClosed(@NonNull EventSource eventSource) {
                super.onClosed(eventSource);
                LogUtils.i(TAG, "onClosed: ");
                ChatLink.this.eventSource = null;
                chatSourceListener.onClosed(message);
            }

            @Override
            public void onEvent(@NonNull EventSource eventSource, @Nullable String id, @Nullable String type, @NonNull String data) {
                super.onEvent(eventSource, id, type, data);
                ChatLink.this.eventSource = eventSource;
                LogUtils.i(TAG, "onEvent: " + data);

                if (!data.equals("[DONE]")) {
                    try {
                        AiGhatCompletionResponse jsonBean = new ObjectMapper().readValue(data, AiGhatCompletionResponse.class);

                        if (jsonBean != null) {
                            if (message == null) {
                                message = jsonBean.getChoices().get(0).getDelta();
                            }
                            if (jsonBean.getChoices() != null && jsonBean.getChoices().get(0).getDelta().getContent() != null) {

                                Message jsonMessage = jsonBean.getChoices().get(0).getDelta();

                                String value = "";
                                if (message.getContent() != null) {
                                    value += message.getContent();
                                }
                                if (jsonMessage.getContent() != null) {
                                    value += jsonMessage.getContent();
                                }
                                message.setContent(value);
                                chatSourceListener.onEvent(message);
                            }
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        chatSourceListener.onThrowable(e, LinkEorr.JSON);
                        chatSourceListener.onClosed(message);
                        LogUtils.i(TAG, "JsonProcessingException: " + e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                super.onFailure(eventSource, t, response);
                LogUtils.i(TAG, "onFailure Throwable" + t + " Response:" + response);
                ChatLink.this.eventSource = eventSource;

                if (t != null) {
                    if (t.getMessage().contains("Failed to connect to")) {
                        chatSourceListener.onThrowable(t, LinkEorr.CONNECT);
                        return;
                    }
                    if (t.getMessage().contains("stream was reset: CANCEL")) {
                        chatSourceListener.onClosed(message);
                        return;
                    }

                }
                if (response!=null){
                    if (response.code()==401){
                        chatSourceListener.onThrowable(t, LinkEorr.APIFAIL);
                        return;
                    }
                }

                ChatLink.this.eventSource = null;
                chatSourceListener.onThrowable(t, LinkEorr.UNKNOWN);
            }

            @Override
            public void onOpen(@NonNull EventSource eventSource, @NonNull Response response) {
                super.onOpen(eventSource, response);
                LogUtils.i(TAG, "onOpen: ");
                ChatLink.this.eventSource = eventSource;
                message = null;
                chatSourceListener.onOpen(response);

            }
        });
    }


    private EventSource eventSource;


    public void stop() {
        if (eventSource != null) {
            eventSource.cancel();
            eventSource = null;
        }
    }

}
