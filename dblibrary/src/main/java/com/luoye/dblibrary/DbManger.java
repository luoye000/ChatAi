package com.luoye.dblibrary;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.luoye.dblibrary.dao.ChatApiDao;
import com.luoye.dblibrary.dao.ChoiceDao;
import com.luoye.dblibrary.dao.MessageDao;
import com.luoye.dblibrary.db.ChatApi;
import com.luoye.dblibrary.db.Choice;
import com.luoye.dblibrary.db.Message;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by: luoye
 * Time: 2022/9/6
 * user:
 */
@Database(version = 1, entities = {ChatApi.class, Choice.class, Message.class}, exportSchema = true)
public abstract class DbManger extends RoomDatabase {

    public abstract ChatApiDao chatApiDao();

    public abstract ChoiceDao choiceDao();

    public abstract MessageDao messageDao();

    private static volatile DbManger INSTANCE;
    private static CompositeDisposable compositeDisposable;

    public static void init(Application context) {
        if (INSTANCE == null) {
            synchronized (DbManger.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, DbManger.class, "ai_chat.db").build();
                    compositeDisposable = new CompositeDisposable();
                }
            }
        }
    }


    public static DbManger Db() {
        if (INSTANCE == null) throw new NullPointerException("DbManagerManger no init");
        return INSTANCE;
    }

    public void add(Completable completable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(completable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
    }

    public void add(Completable completable, Action action) {

        if (compositeDisposable != null) {
            compositeDisposable.add(completable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(action));
        }
    }

    public void add(Completable completable, Action action, Consumer<? super Throwable> onThrowable) {

        if (compositeDisposable != null) {
            compositeDisposable.add(completable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(action, onThrowable));
        }
    }


    public <T> void add(Single<T> single, Consumer<? super T> onSuccess) {

        if (compositeDisposable != null) {
            compositeDisposable.add(single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onSuccess));
        }
    }


    public <T> void add(Single<T> single, Consumer<? super T> onSuccess, Consumer<? super Throwable> onThrowable) {

        if (compositeDisposable != null) {
            compositeDisposable.add(single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onSuccess, onThrowable));
        }
    }


    public void clear() {
        if (compositeDisposable != null) compositeDisposable.clear();
    }


}
