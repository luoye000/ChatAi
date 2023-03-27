package com.luoye.chat.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luoye.apptool.databinding.BaseDataAllFragment;
import com.luoye.apptool.utils.BaseUtils;
import com.luoye.chat.R;
import com.luoye.chat.adapter.AdapterHistory;
import com.luoye.chat.databinding.FragmentHomeBinding;
import com.luoye.chat.model.HomeViewModel;
import com.luoye.chat.model.MainViewModel;
import com.luoye.chat.utils.ToolbarMenuStyleUtils;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.db.Choice;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user: 首页
 */
public class HomeFragment extends BaseDataAllFragment<FragmentHomeBinding, MainViewModel, HomeViewModel> {


    private AdapterHistory adapterHistory;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initFragment() {
        //设置页面menu样式
        ToolbarMenuStyleUtils.upDatesAll(activityViewModel.getMenuItemLiveData().getValue(), true, false, R.id.menu_delete);
        //设置页面menu样式
        activityViewModel.getMenuItemLiveData().observe(this, menuItem -> {
            ToolbarMenuStyleUtils.upDatesAll(menuItem, true, false, R.id.menu_delete);
        });

        //观察 menu事件
        activityViewModel.getMenuStyleLiveData().observe(this, menuItem -> {
            // menu_delete事件
            if (menuItem.isDistribute() && menuItem.getMenuItem().getItemId() == R.id.menu_delete) {
                BaseUtils.showNormalDialog(context,
                        context.getString(R.string.delete_history),
                        context.getString(R.string.delete_history_message),
                        context.getString(R.string.cancel), (DialogInterface.OnClickListener) (dialog, which) -> {
                        },
                        context.getString(R.string.confirm), (DialogInterface.OnClickListener) (dialog, which) -> {
                            DbManger db = DbManger.Db();
                            db.add(db.choiceDao().delete(viewModel.getChoiceListLiveData().getValue().toArray(new Choice[]{})), () -> {
                                viewModel.getChoiceListLiveData().postValue(new ArrayList<>());
                            });
                        });

            }
        });


        adapterHistory = new AdapterHistory(context, this, new ArrayList<>());
        binding.recyclerView.setLayoutManager(BaseUtils.getLinearLayoutManager(context, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(adapterHistory);

        viewModel.getChoiceListLiveData().observe(this, choices -> {
            if (adapterHistory != null) adapterHistory.setObjectArrayList(choices);
        });
        DbManger.Db().add(DbManger.Db().choiceDao().queryAll(), choices -> viewModel.getChoiceListLiveData().postValue(choices));

        binding.openChat.setOnClickListener(v -> {

            Choice choice = new Choice();
            choice.setName(getString(R.string.cache_name));
            choice.setInitTime(System.currentTimeMillis());
            DbManger.Db().add(DbManger.Db().choiceDao().insert(choice), aLong -> {

                choice.setId(aLong);
                //切换
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment().setChoice(choice));

            });


        });

    }
}
