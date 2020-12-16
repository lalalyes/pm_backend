package com.fudan.pm.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequest {
    @NotNull(message = "activityId为空")
    private int activityId;
    @NotNull(message = "评论内容不能为空")
    @NotBlank(message = "评论内容不能为空")
    private String text;
    @NotNull(message = "评分为空")
    @Max(5)
    @Min(1)
    private int score;

    public CommentRequest() {}


    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

