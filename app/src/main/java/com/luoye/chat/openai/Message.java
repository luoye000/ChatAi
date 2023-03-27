package com.luoye.chat.openai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user:
 */
@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    /**
     * 目前支持三中角色参考官网，进行情景输入：https://platform.openai.com/docs/guides/chat/introduction
     */
    private String role;
    private String content;

    public static Message ofUser(String content) {
        return new Message(Role.USER.getValue(), content);
    }

    public static Message ofSystem(String content) {
        return new Message(Role.SYSTEM.getValue(), content);
    }

    public static Message ofAssistant(String content) {
        return new Message(Role.ASSISTANT.getValue(), content);
    }

    @Getter
    @AllArgsConstructor
    public enum Role {

        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");
        private String value;
    }

}
