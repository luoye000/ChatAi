package com.luoye.chat.ui;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.luoye.apptool.OnBaseListener;
import com.luoye.apptool.databinding.activity.BaseDataAppCompatActivity;
import com.luoye.apptool.utils.BaseUtils;
import com.luoye.chat.adapter.AdapterApi;
import com.luoye.chat.info.ApiInfo;
import com.luoye.chat.R;
import com.luoye.chat.databinding.ActivityApiBinding;
import com.luoye.chat.info.ApiStateInfo;
import com.luoye.chat.model.ApiViewModel;
import com.luoye.chat.utils.StringUtils;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.db.ChatApi;

import java.util.ArrayList;
import java.util.List;


public class ApiActivity extends BaseDataAppCompatActivity<ActivityApiBinding, ApiViewModel> {


    private AdapterApi adapterApi;
    private final DbManger dbManger = DbManger.Db();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_api;
    }

    @Override
    protected void initActivity() {


        binding.addKeyInclude.setDate(viewModel);
        binding.setDate(viewModel);
        viewModel.getAddKeyLiveData().postValue(false);
        adapterApi = new AdapterApi(context, this, new ArrayList<>());

        adapterApi.setOnBaseListener(new OnBaseListener<ChatApi>() {
            @Override
            public void itemDate(ChatApi chatApi) {
                dbManger.add(dbManger.chatApiDao().queryChatApiState(ApiStateInfo.OPEN.getState()), chatApis -> {
                    for (ChatApi chatApi1 : chatApis) {
                        chatApi1.setApiState(ApiStateInfo.CLOSURE.getState());
                    }
                    chatApis.add(chatApi);
                    dbManger.add(dbManger.chatApiDao().upDate(chatApis.toArray(new ChatApi[]{})), () -> {
                        initOpenKey();
                        initRecyclerView();
                    }, e -> {
                    });
                }, e -> {
                });
            }
        });


        viewModel.getListMediatorLiveData().observe(this, chatApis -> {
            adapterApi.setObjectArrayList(chatApis);
        });


        binding.recyclerView.setLayoutManager(BaseUtils.getLinearLayoutManager(context, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapterApi);


        binding.openKeyInclude.apiValue.setText("");
        binding.openKeyInclude.delete.setVisibility(View.INVISIBLE);
        binding.openKeyInclude.open.setVisibility(View.INVISIBLE);


        binding.addKeyInclude.getRoot().setVisibility(View.GONE);


        binding.addKey.setOnClickListener(v -> viewModel.getAddKeyLiveData().postValue(true));


        binding.addKeyInclude.cancel.setOnClickListener(v -> {
            viewModel.getEditKeyLiveData().postValue("");
            viewModel.getEditKeyNameLiveData().postValue("");
            viewModel.getAddKeyLiveData().postValue(false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.addKeyInclude.editKeytext.getWindowToken(), 0);
        });


        binding.addKeyInclude.submit.setOnClickListener(v -> {
            String key = viewModel.getEditKeyLiveData().getValue();
            String keyName = viewModel.getEditKeyNameLiveData().getValue();

            if (key == null || key.equals("")) {
                BaseUtils.makeLongText(context, "请填写KEY");
                return;
            }
            if (keyName == null || keyName.equals("")) {
                BaseUtils.makeLongText(context, "请填写KEY的别名");
                return;
            }


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.addKeyInclude.editKeytext.getWindowToken(), 0);
            ChatApi chatApi = new ChatApi();
            chatApi.setApi(key);
            chatApi.setApiAlias(keyName);
            chatApi.setApiType(ApiInfo.GhatGTP.getType());
            chatApi.setApiState(ApiStateInfo.CLOSURE.getState());
            dbManger.add(dbManger.chatApiDao().insert(chatApi), this::initRecyclerView, e -> {
            });
            viewModel.getEditKeyLiveData().postValue("");
            viewModel.getEditKeyNameLiveData().postValue("");
            viewModel.getAddKeyLiveData().postValue(false);

        });

        initRecyclerView();
        initOpenKey();
    }


    private void initOpenKey() {
        dbManger.add(dbManger.chatApiDao().queryChatApiState(ApiStateInfo.OPEN.getState()), chatApis -> {

            if (chatApis.size() > 0) {
                for (ChatApi chatApi : chatApis) {
                    binding.openKeyInclude.apiValue.setText(chatApi.getApiAlias());
                }
            } else {
                binding.openKeyInclude.apiValue.setText(R.string.add_api_key_open_value);
            }
        }, e -> {
        });
    }


    private void initRecyclerView() {
        dbManger.add(dbManger.chatApiDao().queryAllChatApi(), chatApis -> {
                    viewModel.getListMediatorLiveData().postValue(chatApis);
                }
                , e -> {
                });
    }
}