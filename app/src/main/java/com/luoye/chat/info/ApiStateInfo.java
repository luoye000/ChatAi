package com.luoye.chat.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user: api 状态信息
 */

@Getter
@AllArgsConstructor
public enum ApiStateInfo {

    OPEN("open"), CLOSURE("closure");

    private String state;
}
