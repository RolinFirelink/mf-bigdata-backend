package com.arg.smart.openai.entity;

import lombok.Data;

/**
 * @description: openAi属性
 * @author cgli
 * @date: 2023/2/8 11:31
 */
@Data
public class Completion {
    private String model;
    private String prompt;
    private double temperature = 1;
    private int max_tokens = 16;
    private double top_p = 1;
    private int n = 1;
    private boolean stream = false;
    private double frequency_penalty = 0;
    private double presence_penalty = 0;
}
