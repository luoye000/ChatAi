package com.luoye.dblibrary.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user: 聊天历史
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Choice implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private long upTime; //更新时间

    @ColumnInfo
    private long initTime; //创建时间
}
