package com.arg.smart.openai.controller;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.openai.common.AiOperator;
import com.arg.smart.openai.config.OpenAiConfig;
import com.arg.smart.openai.entity.CompletionResult;
import com.arg.smart.openai.entity.Question;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @description: 聊天机器人
 * @author cgli
 * @date: 2023/2/8 11:46
 */
@Slf4j
@Api(tags = "chatGpt")
@RestController
@RequestMapping("/openai")
public class OpenAiController {
    @Resource
    OpenAiConfig openAiConfig;

    @PostMapping("/answer")
    public Result<CompletionResult> answer(@RequestBody Question question) throws IOException {
        Result<String> result = AiOperator.answerMyQuestion(openAiConfig.getUrl(), openAiConfig.getToken(), question.getData());
        return Result.buildResult(new CompletionResult().setId(question.getId())
                .setResult(result.getData()), result.getCode(), result.getMsg());
    }
}
