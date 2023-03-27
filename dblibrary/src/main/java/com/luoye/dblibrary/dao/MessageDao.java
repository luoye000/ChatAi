package com.luoye.dblibrary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.luoye.dblibrary.db.Choice;
import com.luoye.dblibrary.db.Message;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    Single<List<Message>> queryAll();

    @Insert
    Completable insert(Message... messages);

    @Update
    Completable upDate(Message... messages);

    @Delete
    Completable delete(Message... messages);
}
