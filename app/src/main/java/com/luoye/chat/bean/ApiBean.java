package com.luoye.chat.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApiBean {


    //api
    private String api;

    //是否启用
    private boolean isOpen;


}
