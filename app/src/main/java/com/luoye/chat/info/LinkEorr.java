package com.luoye.chat.info;

import com.luoye.chat.MyApplication;
import com.luoye.chat.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
@Getter
@AllArgsConstructor
public enum LinkEorr {

    APIFAIL(MyApplication.getApplication().getString(R.string.api_fail)),
    CONNECT(MyApplication.getApplication().getString(R.string.network_eorr)),
    UNKNOWN("unknown"),
    JSON("JSON parsing error");

    private String type;
}
