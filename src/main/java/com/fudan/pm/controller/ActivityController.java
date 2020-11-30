package com.fudan.pm.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fudan.pm.Tool;
import com.fudan.pm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Validated
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/activityList")
    public ResponseEntity<?> activityList(){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.activityList(username);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/enrolledActivity")//查看已报名活动列表
    public ResponseEntity<?> enrolledActivity(){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.enrolledActivity(username);
        return Tool.getResponseEntity(result);
    }

}
