package com.luoye.chat.ui;


import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luoye.apptool.databinding.BaseDataAllFragment;
import com.luoye.apptool.utils.BaseUtils;
import com.luoye.chat.R;
import com.luoye.chat.adapter.ChatBubbleAdapter;
import com.luoye.chat.databinding.FragmentChatBinding;
import com.luoye.chat.info.LinkEorr;
import com.luoye.chat.info.LinkState;
import com.luoye.chat.model.ChatViewModel;
import com.luoye.chat.model.MainViewModel;
import com.luoye.chat.openai.AiChatCompletion;
import com.luoye.chat.openai.AiGhatCompletionResponse;
import com.luoye.chat.openai.ChatLink;
import com.luoye.chat.openai.ChatSourceListener;
import com.luoye.chat.utils.ToolbarMenuStyleUtils;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.db.Choice;
import com.luoye.dblibrary.db.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.Response;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user: AI聊天
 */

@AndroidEntryPoint
public class ChatFragment extends BaseDataAllFragment<FragmentChatBinding, MainViewModel, ChatViewModel> {
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_chat;
    }


    private Choice choice;

    @Inject
    ChatLink chatLink;


    private ChatBubbleAdapter chatBubbleAdapter;


    @Override
    protected void initFragment() {

        //设置页面menu样式
        ToolbarMenuStyleUtils.upDatesAll(activityViewModel.getMenuItemLiveData().getValue(), true, false, -1);
        //设置页面menu样式
        activityViewModel.getMenuItemLiveData().observe(this, menuItem -> {
            ToolbarMenuStyleUtils.upDatesAll(menuItem, true, false, -1);
        });


        binding.setData(viewModel);

        choice = ChatFragmentArgs.fromBundle(getArguments()).getChoice();

        chatBubbleAdapter = new ChatBubbleAdapter(context, this, new ArrayList<>());
        binding.recyclerView.setLayoutManager(BaseUtils.getLinearLayoutManager(context, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(chatBubbleAdapter);


        viewModel.getListMessageLiveData().observe(this, messages -> {
            chatBubbleAdapter.setObjectArrayList(messages);
        });

//
//        viewModel.getLinkStateLiveData().observe(this, linkState -> {
//
//            if (linkState == LinkState.IDLE) {
//                chatLink.stop();
//            }
//        });


        viewModel.getLinkStateLiveData().postValue(LinkState.IDLE);


        DbManger.Db().add(DbManger.Db().choiceDao().queryChoiceMessage(choice.getId()), messages -> {
            viewModel.getListMessageLiveData().postValue(messages);
        });


        binding.link.setOnClickListener(v -> {
            if (viewModel.getLinkStateLiveData().getValue() == LinkState.CALL) {
                chatLink.stop();
            } else {
                link();
            }
        });


    }


    private void link() {
        String value = binding.editText.getText().toString();
        if (!value.equals("")) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.editText.getWindowToken(), 0);
            binding.editText.clearFocus();

            List<Message> list = viewModel.getListMessageLiveData().getValue();
            List<Message> messageList = list == null ? new ArrayList<>() : list;
            messageList.add(Message.ofUser(value, choice));
            viewModel.getListMessageLiveData().postValue(messageList);

            binding.editText.setText("");
            binding.editText.setHint("AI运行中...");

            chatLink.link(AiChatCompletion.builder().messages(messageList).stream(true).build(), new ChatSourceListener() {
                @Override
                public void onOpen(@NonNull Response response) {
                    viewModel.getLinkStateLiveData().postValue(LinkState.CALL);
                }

                @Override
                public void onClosed(Message message) {
                    Message oldMessage = messageList.get(messageList.size() - 1);
                    message.setChoiceId(choice.getId());
                    if (oldMessage.getRole().equals(message.getRole())) {
                        messageList.remove(oldMessage);
                    }
                    messageList.add(message);
                    if (messageList.size() >= 2) {
                        DbManger.Db().add(DbManger.Db().messageDao().insert(messageList.get(messageList.size() - 2), messageList.get(messageList.size() - 1)), () -> {

                        }, throwable -> {

                        });
                    }


                    if (choice.getName() == null || choice.getName().equals("") || choice.getName().equals(context.getString(R.string.cache_name))) {
                        String content = messageList.get(0).getContent();
                        if (content.length() < 5) {
                            content += new SimpleDateFormat(" MM/dd HH:mm").format(new Date());
                        }
                        choice.setName(content);
                        DbManger.Db().add(DbManger.Db().choiceDao().upDate(choice));
                    }
                    viewModel.getLinkStateLiveData().postValue(LinkState.IDLE);
                }

                @Override
                public void onEvent(Message message) {
                    Message oldMessage = messageList.get(messageList.size() - 1);
                    message.setChoiceId(choice.getId());
                    if (oldMessage.getRole().equals(message.getRole())) {
                        messageList.remove(oldMessage);
                    }
                    messageList.add(message);
                    viewModel.getListMessageLiveData().postValue(messageList);

                }

                @Override
                public void onThrowable(Throwable throwable, LinkEorr linkEorr) {
                    viewModel.getLinkStateLiveData().postValue(LinkState.EORR);
                    activity.runOnUiThread(() -> BaseUtils.makeShortText(context, linkEorr.getType()));

                }
            });
        } else {
            BaseUtils.makeLongText(context, "数据不能为空");
        }
    }

}
