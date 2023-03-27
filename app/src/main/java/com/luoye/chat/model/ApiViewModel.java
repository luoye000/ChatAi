package com.luoye.chat.model;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.luoye.dblibrary.db.ChatApi;

import java.util.List;

import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */
@Getter
public class ApiViewModel extends ViewModel {

    private final MediatorLiveData<List<ChatApi>> listMediatorLiveData = new MediatorLiveData<>();

    private final MediatorLiveData<String> editKeyLiveData = new MediatorLiveData<>();

    private final MediatorLiveData<String> editKeyNameLiveData = new MediatorLiveData<>();

    private final MediatorLiveData<Boolean> addKeyLiveData = new MediatorLiveData<>();


}
