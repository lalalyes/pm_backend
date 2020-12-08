package com.fudan.pm.controller.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ChangePasswordRequest {
    @NotNull(message = "旧密码不能为空")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 12, message = "密码长度必须在5位到12位之间")
    private String newPassword;

    public ChangePasswordRequest() {}


    public String getOldPassword() {
        return oldPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }
}
