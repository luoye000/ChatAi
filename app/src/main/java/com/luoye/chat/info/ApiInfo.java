package com.luoye.chat.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user: 支持的API信息
 */

@Getter
@AllArgsConstructor
public enum ApiInfo {

    GhatGTP("chat_gtp");

    private String type;


}
