package com.luoye.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import com.luoye.apptool.utils.BaseUtils;
import com.luoye.apptool.viewbinding.BaseViewAdapter;
import com.luoye.chat.R;
import com.luoye.chat.databinding.AdapterApiBinding;
import com.luoye.chat.info.ApiStateInfo;
import com.luoye.chat.openai.ChatLinkInfo;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.db.ChatApi;

import java.util.List;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */
public class AdapterApi extends BaseViewAdapter<ChatApi, AdapterApiBinding> {


    public AdapterApi(Context context, LifecycleOwner owner, List<ChatApi> objectArrayList) {
        super(context, owner, objectArrayList);
    }

    @Override
    protected void initAdapter(BaseViewAdapter<ChatApi, AdapterApiBinding>.ViewHolder viewHolder, ChatApi chatApi, int i) {
        viewHolder.binding.apiValue.setText(chatApi.getApiAlias());
        if (chatApi.getApiState().equals(ApiStateInfo.OPEN.getState())) {
            viewHolder.binding.open.setText(R.string.disabled);
            viewHolder.binding.open.setVisibility(View.VISIBLE);
            viewHolder.binding.delete.setVisibility(View.GONE);
        } else if (chatApi.getApiState().equals(ApiStateInfo.CLOSURE.getState())) {
            viewHolder.binding.open.setText(R.string.open);
            viewHolder.binding.open.setVisibility(View.VISIBLE);
            viewHolder.binding.delete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.binding.open.setVisibility(View.INVISIBLE);
        }
        viewHolder.binding.open.setOnClickListener(v -> {
            if (chatApi.getApiState().equals(ApiStateInfo.OPEN.getState())) {
                chatApi.setApiState(ApiStateInfo.CLOSURE.getState());
                ChatLinkInfo.KeyUser="";
                if (onBaseListener != null) onBaseListener.itemDate(chatApi);
            } else if (chatApi.getApiState().equals(ApiStateInfo.CLOSURE.getState())) {
                chatApi.setApiState(ApiStateInfo.OPEN.getState());
                ChatLinkInfo.KeyUser=chatApi.getApi();
                if (onBaseListener != null) onBaseListener.itemDate(chatApi);
            }
        });

        viewHolder.binding.delete.setOnClickListener(v -> {
            BaseUtils.showNormalDialog(context,
                    context.getString(R.string.delete_key),
                    context.getString(R.string.delete_key_message),
                    context.getString(R.string.cancel), (DialogInterface.OnClickListener) (dialog, which) -> {
                    },
                    context.getString(R.string.confirm), (DialogInterface.OnClickListener) (dialog, which) -> {
                        DbManger db = DbManger.Db();
                        db.add(db.chatApiDao().delete(chatApi), () -> {
                            objectArrayList.remove(chatApi);
                            notifyDataSetChanged();
                        });
                    });
        });


    }
}
