package com.luoye.dblibrary.db;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user: 对话内容
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(foreignKeys = @ForeignKey(entity = Choice.class, parentColumns = "id", childColumns = "choiceId", onDelete = CASCADE), indices = @Index(value = {"choiceId"}))
public class Message {

    @JsonIgnore
    @PrimaryKey(autoGenerate = true)
    private long id;


    @ColumnInfo(name = "choiceId")
    @JsonIgnore
    private long choiceId;

    @ColumnInfo
    private String role;


    @ColumnInfo
    private String content;

    @Ignore
    public static Message ofUser(String content, Choice choice) {
        Message message = new Message();
        message.setChoiceId(choice.getId());
        message.setRole(Role.USER.getValue());
        message.setContent(content);
        return message;
    }

    @Ignore
    public static Message ofSystem(String content, Choice choice) {
        Message message = new Message();
        message.setChoiceId(choice.getId());
        message.setRole(Role.SYSTEM.getValue());
        message.setContent(content);
        return message;
    }

    @Ignore
    public static Message ofAssistant(String content, Choice choice) {
        Message message = new Message();
        message.setChoiceId(choice.getId());
        message.setRole(Role.ASSISTANT.getValue());
        message.setContent(content);
        return message;
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
