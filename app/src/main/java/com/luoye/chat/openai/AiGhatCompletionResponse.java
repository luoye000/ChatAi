package com.luoye.chat.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.luoye.dblibrary.db.Message;

import lombok.Data;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user: Ghat响应数据
 */
@Data
public class AiGhatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;

    @Data
    public static class ChatChoice {
        private long index;
        /**
         * 请求参数 stream 为 true 返回是 delta 开始流式响应
         */
        @JsonProperty("delta")
        private Message delta;
        /**
         * 请求参数 stream 为 false 返回是message 关闭流式响应
         */
        @JsonProperty("message")
        private Message message;
        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Data
    public class Usage {
        @JsonProperty("prompt_tokens")
        private long promptTokens;
        @JsonProperty("completion_tokens")
        private long completionTokens;
        @JsonProperty("total_tokens")
        private long totalTokens;
    }

}
