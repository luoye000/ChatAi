package com.luoye.dblibrary.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
 * user: api 表
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class ChatApi {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String api; //api 数据

    @ColumnInfo
    private String apiAlias;//别名

    @ColumnInfo
    private String apiType; //api 类型

    @ColumnInfo
    private String apiState;// api 状态


}
