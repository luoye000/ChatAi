package com.luoye.chat.openai;

import androidx.annotation.NonNull;

import com.luoye.chat.info.LinkEorr;
import com.luoye.dblibrary.db.Message;

import okhttp3.Response;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user:
 */
public interface ChatSourceListener {

    //开始
    void onOpen(@NonNull Response response);

    //结束
    void onClosed(Message message);

    //数据流
    void onEvent(Message message);

    //出错
    void onThrowable(Throwable throwable, LinkEorr linkEorr);
}
