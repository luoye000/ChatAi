package com.luoye.dblibrary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.luoye.dblibrary.db.ChatApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */
@Dao
public interface ChatApiDao {


    @Query("SELECT * FROM chatapi")
    Single<List<ChatApi>> queryAllChatApi();

    @Query("SELECT * FROM chatapi where id in (:id)")
    Single<List<ChatApi>> queryIdChatApi(long... id);

    @Query("SELECT * FROM chatapi where apiState in (:apiState)")
    Single<List<ChatApi>> queryChatApiState(String... apiState);

    @Insert
    Completable insert(ChatApi... chatApis);

    @Update
    Completable upDate(ChatApi... chatApis);

    @Delete
    Completable delete(ChatApi... chatApis);

}
