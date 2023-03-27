package com.luoye.chat.model;

import android.view.MenuItem;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.luoye.chat.bean.MenuItemStyle;

import java.util.List;

import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */
@Getter
public class MainViewModel extends ViewModel {

    //menuItem事件分发
    private final MediatorLiveData<MenuItemStyle> menuStyleLiveData = new MediatorLiveData<>();

    //menu 样式
    private final MediatorLiveData<List<MenuItem>> menuItemLiveData = new MediatorLiveData<>();

}
