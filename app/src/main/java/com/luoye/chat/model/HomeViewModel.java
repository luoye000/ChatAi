package com.luoye.chat.model;


import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.luoye.dblibrary.db.Choice;

import java.util.List;

import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
@Getter
public class HomeViewModel extends ViewModel {

    //历史对话记录
    private final MediatorLiveData<List<Choice>> choiceListLiveData = new MediatorLiveData<>();

}
