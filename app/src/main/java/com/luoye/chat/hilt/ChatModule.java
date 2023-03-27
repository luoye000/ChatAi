package com.luoye.chat.hilt;


import com.luoye.chat.openai.ChatLink;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
@Module
@InstallIn({SingletonComponent.class})
public class ChatModule {

    @Singleton
    @Provides
    public ChatLink getChatLink(){
        return new ChatLink();
    }

}
