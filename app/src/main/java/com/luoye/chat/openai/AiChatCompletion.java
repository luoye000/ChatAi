package com.luoye.chat.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.luoye.dblibrary.db.Message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by: luoye
 * Time: 2023/3/24
 * user:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiChatCompletion implements Serializable {

    @NonNull
    @Builder.Default
    private String model = Model.GPT_3_5_TURBO.getName();

    @NonNull
    private List<Message> messages;
    /**
     * 使用什么取样温度，0到2之间。越高越奔放。越低越保守。
     * <p>
     * 不要同时改这个和topP
     */
    @Builder.Default
    private double temperature = 0.9;

    /**
     * 0-1
     * 建议0.9
     * 不要同时改这个和temperature
     */
    @JsonProperty("top_p")
    @Builder.Default
    private double topP = 0.9;


    /**
     * 结果数。
     */
    @Builder.Default
    private Integer n = 1;


    /**
     * 是否流式输出.
     * default:false
     */
    @Builder.Default
    private boolean stream = false;

    /**
     * 停用词
     */
    private List<String> stop;
    /**
     * 3.5 最大支持4096
     * 4.0 最大32k
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens;


    @JsonProperty("presence_penalty")
    private double presencePenalty;

    /**
     * -2.0 ~~ 2.0
     */
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty;

    @JsonProperty("logit_bias")
    private Map logitBias;

    /**
     * 用户唯一值，确保接口不被重复调用
     */
    private String user;


    @Getter
    @AllArgsConstructor
    public enum Model {
        /**
         * gpt-3.5-turbo
         */
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        /**
         * GPT4.0
         */
        GPT_4("gpt-4"),
        /**
         * GPT4.0 超长上下文
         */
        GPT_4_32K("gpt-4-32k");
        private String name;
    }

}


