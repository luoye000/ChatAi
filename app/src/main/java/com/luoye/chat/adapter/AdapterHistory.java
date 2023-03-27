package com.luoye.chat.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;

import com.luoye.apptool.utils.BaseUtils;
import com.luoye.apptool.viewbinding.BaseViewAdapter;
import com.luoye.chat.R;
import com.luoye.chat.databinding.AdapterHistoryBinding;
import com.luoye.chat.ui.HomeFragmentDirections;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.db.Choice;

import java.util.List;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user: 历史对话
 */
public class AdapterHistory extends BaseViewAdapter<Choice, AdapterHistoryBinding> {

    public AdapterHistory(Context context, LifecycleOwner owner, List<Choice> objectArrayList) {
        super(context, owner, objectArrayList);
    }

    @Override
    protected void initAdapter(BaseViewAdapter<Choice, AdapterHistoryBinding>.ViewHolder viewHolder, Choice choice, int i) {

        viewHolder.binding.textValue.setText(choice.getName());
        viewHolder.binding.delete.setOnClickListener(v -> {
            BaseUtils.showNormalDialog(context,
                    context.getString(R.string.delete_history),
                    context.getString(R.string.delete_history_message),
                    context.getString(R.string.cancel), (DialogInterface.OnClickListener) (dialog, which) -> {
                    },
                    context.getString(R.string.confirm), (DialogInterface.OnClickListener) (dialog, which) -> {
                        DbManger db = DbManger.Db();
                        db.add(db.choiceDao().delete(choice), () -> {
                            objectArrayList.remove(choice);
                            notifyDataSetChanged();
                        });
                    });
        });

        viewHolder.itemView.setOnClickListener(v -> {
            //切换
            Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment().setChoice(choice));
        });

    }
}
