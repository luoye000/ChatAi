package com.luoye.chat;

import android.app.Application;

import com.luoye.apptool.utils.LogUtils;
import com.luoye.dblibrary.DbManger;
import com.luoye.dblibrary.dao.ChatApiDao;
import com.luoye.dblibrary.dao.ChoiceDao;
import com.luoye.dblibrary.dao.MessageDao;
import com.luoye.dblibrary.db.Choice;
import com.luoye.dblibrary.db.Message;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by: luoye
 * Time: 2023/3/25
 * user:
 */
@HiltAndroidApp
public class MyApplication extends Application {


    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DbManger.init(this);


        ChoiceDao choiceDao = DbManger.Db().choiceDao();
        MessageDao messageDao = DbManger.Db().messageDao();


//        Choice choice = new Choice();
//        choice.setName("一个测试对话");
//        choice.setInitTime(System.currentTimeMillis());
//        choice.setUpTime(0);
//
//        Choice choice2 = new Choice();
//        choice2.setName("一个测试对话");
//        choice2.setInitTime(System.currentTimeMillis());
//        choice2.setUpTime(0);
//
//        DbManger.Db().add(choiceDao.insert(choice,choice2));



//        DbManger.Db().add(choiceDao.queryAll(), choices -> {
//            LogUtils.i("---", "List<choice> 查询成功：" + choices.size() + "个");
//            for (Choice choice : choices) {
//                LogUtils.i("---", "choice：" + choice.toString());
//            }
//
//        });
//
//        DbManger.Db().add(messageDao.queryAll(), messages -> {
//            LogUtils.i("---", "List<messages> 查询成功：" + messages.size() + "个");
//            for (Message message : messages) {
//                LogUtils.i("---", "message：" + message.toString());
//            }
//        });


//        Choice choice = new Choice();
//        choice.setName("一个测试对话");
//        choice.setInitTime(System.currentTimeMillis());
//        choice.setUpTime(0);
//
//
//        DbManger.Db().add(choiceDao.insert(choice), new Action() {
//            @Override
//            public void run() throws Exception {
//                LogUtils.i("---", "choice插入成功");
//                DbManger.Db().add(choiceDao.queryAll(), choices -> {
//                    LogUtils.i("---", "List<choice> 查询成功：" + choices.size() + "个");
//
//                    Choice choice = choices.get(0);
//
//                    LogUtils.i("---", "choice：" + choices.toString());
//
//                    Message message1 = new Message();
//                    message1.setRole("A");
//                    message1.setChoiceId(choice.getId());
//                    message1.setContent("1测试数据》》》》》》");
//                    Message message2 = new Message();
//                    message2.setRole("B");
//                    message2.setChoiceId(choice.getId());
//                    message2.setContent("2测试数据》》》》》》");
//                    Message message3 = new Message();
//                    message3.setRole("C");
//                    message3.setChoiceId(choice.getId());
//                    message3.setContent("3测试数据》》》》》》");
//
//                    DbManger.Db().add(messageDao.insert(message1, message2, message3), () -> {
//
//                        LogUtils.i("---", "Messages插入成功");
//
//                        DbManger.Db().add(choiceDao.queryChoiceMessage(choice.getId()), messages -> {
//                            for (Message message : messages) {
//                                LogUtils.i("---", "message：" + message.toString());
//
//                            }
//                        });
//
//
////                        DbManger.Db().add(choiceDao.queryAll(), choices1 -> {
////                            LogUtils.i("---", "List<Choice> 查询成功：" + choices1.size() + "个");
////                            for (Choice choice1 : choices1) {
////                                LogUtils.i("---", "choice：" + choice1.toString());
////
////
////
////                            }
////                        });
//
//                    });
//
//
//                });
//            }
//        });


    }
}
