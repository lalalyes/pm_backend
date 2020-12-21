package com.fudan.pm.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class EditRequest {
    @NotNull(message = "activityId不能为空")
    private int activityId;
    @NotNull(message = "活动名称不能为空")
    @NotBlank(message = "活动名称不能为空")
    private String activityName;
    @NotNull(message = "introduction不能为空")
    @NotBlank(message = "introduction不能为空")
    private String introduction;
    @NotNull(message = "type不能为空")
    @NotBlank(message = "type不能为空")
    private String type;
    @NotNull(message = "venueId不能为空")
    private int venueId;
    @NotNull(message = "limit不能为空")
    @Min(1)
    private int limit;
    @NotNull(message = "activityStartTime不能为空")
    private Date activityStartTime;
    @NotNull(message = "activityEndTime不能为空")
    private Date activityEndTime;
    @NotNull(message = "signUpStartTime不能为空")
    private Date signUpStartTime;
    @NotNull(message = "signUpEndTime不能为空")
    private Date signUpEndTime;
    public EditRequest() {}

    public int getActivityId() {
        return activityId;
    }

    public int getVenueId() {
        return venueId;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public Date getSignUpEndTime() {
        return signUpEndTime;
    }

    public Date getSignUpStartTime() {
        return signUpStartTime;
    }

    public int getLimit() {
        return limit;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getType() {
        return type;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSignUpEndTime(Date signUpEndTime) {
        this.signUpEndTime = signUpEndTime;
    }

    public void setSignUpStartTime(Date signUpStartTime) {
        this.signUpStartTime = signUpStartTime;
    }

    public void setType(String type) {
        this.type = type;
    }
}
