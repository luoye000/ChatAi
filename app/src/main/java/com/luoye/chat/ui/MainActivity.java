package com.luoye.chat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.luoye.apptool.databinding.activity.BaseDataAppCompatActivity;
import com.luoye.apptool.utils.BaseUtils;
import com.luoye.chat.R;
import com.luoye.chat.bean.MenuItemStyle;
import com.luoye.chat.databinding.ActivityMainBinding;
import com.luoye.chat.databinding.NavHeaderMainBinding;
import com.luoye.chat.model.MainViewModel;
import com.luoye.chat.utils.DrawerLeftEdge;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseDataAppCompatActivity<ActivityMainBinding, MainViewModel> {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private NavHeaderMainBinding bindingNavHeader;



    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActivity() {
        //设置 toolbar
        setSupportActionBar(binding.appBarMain.toolbar);

        //获取 NavHeader布局
        bindingNavHeader = NavHeaderMainBinding.inflate(getLayoutInflater());

        //添加NavHeader布局到navView
        binding.navView.addHeaderView(bindingNavHeader.getRoot());


        //获取Navigation
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        //配置抽屉布局
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //抽屉移动监听
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                //解决抽屉无法全屏划出  1.0f 为全屏
                DrawerLeftEdge.setDrawerLeftEdgeSize(MainActivity.this, binding.drawerLayout, 0.5f);

            }
        });


        //设置 用户头像
        bindingNavHeader.userIco.setOnClickListener(view -> {
            BaseUtils.makeLongText(context, getString(R.string.not_open));
            binding.drawerLayout.closeDrawers();
            // navController.navigate(R.id.action_home_to_login);
        });




        //悬浮
        ((SwitchCompat) binding.navView.getMenu().findItem(R.id.nav_window).getActionView().findViewById(R.id.menu_switchCompat)).setOnCheckedChangeListener((compoundButton, b) -> {

            //判断悬浮窗权限
            if (Settings.canDrawOverlays(this)) {
                //todo 开启悬浮窗
                BaseUtils.makeLongText(context, getString(R.string.not_open));
                compoundButton.setChecked(false);
            } else {
                BaseUtils.showNormalDialog(context, getString(R.string.dialog_window_title), getString(R.string.dialog_window_value),
                        getString(R.string.dialog_window_confirm), (DialogInterface.OnClickListener) (dialogInterface, i) -> {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            } catch (Exception e) {
                                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION));
                            }
                        },
                        getString(R.string.dialog_window_cancel), null);
                compoundButton.setChecked(false);
            }
        });


        //抽屉 menu事件监听
        binding.navView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.nav_api:
                    BaseUtils.startActivity(context,ApiActivity.class);
                    binding.drawerLayout.closeDrawers();
                    break;
            }
            return false;
        });

    }

    //配置 右上角工具栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        List<MenuItem> menuItemList = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            menuItemList.add(menu.getItem(i));
        }
        viewModel.getMenuItemLiveData().postValue(menuItemList);
        return true;
    }


    //配置 左上角工具栏
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }


    //工具栏 左上角事件监听
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        viewModel.getMenuStyleLiveData().postValue(new MenuItemStyle(item, true));
        return super.onOptionsItemSelected(item);
    }
}