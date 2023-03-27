package com.luoye.dblibrary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
public interface ChoiceDao {


    @Query("SELECT * FROM choice")
    Single<List<Choice>> queryAll();

    @Query("SELECT * FROM message where choiceId in (:choiceId)")
    Single<List<Message>> queryChoiceMessage(long choiceId);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insert(Choice choice);

    @Insert
    Completable insert(Choice... choices);

    @Update
    Completable upDate(Choice... choices);

    @Delete
    Completable delete(Choice... choices);
}
