package com.luoye.chat.model;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.luoye.chat.info.LinkState;
import com.luoye.dblibrary.db.Message;

import java.util.List;

import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
@Getter
public class ChatViewModel extends ViewModel {

    private final MediatorLiveData<List<Message>> listMessageLiveData = new MediatorLiveData<>();

    private final MediatorLiveData<LinkState> linkStateLiveData = new MediatorLiveData<>();


}
