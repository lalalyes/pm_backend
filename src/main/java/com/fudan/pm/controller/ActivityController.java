package com.fudan.pm.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fudan.pm.Tool;
import com.fudan.pm.controller.request.CommentRequest;
import com.fudan.pm.controller.request.EditRequest;
import com.fudan.pm.domain.Activity;
import com.fudan.pm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

@RestController
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Validated
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/searchActivity")
    public ResponseEntity<?> searchActivity(@Validated @RequestParam(value = "content")String content, @Validated @RequestParam(value = "type")int type){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.searchActivity(content, type);
        return Tool.getResponseEntity(result);
    }

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

    @GetMapping("/activityDetails")
    public ResponseEntity<?> activityDetails(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.activityDetails(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/activityEnrollment")
    public ResponseEntity<?> activityEnrollment(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.activityEnrollment(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/retreatEnrollment")
    public ResponseEntity<?> retreatEnrollment(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.retreatEnrollment(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/activityCheckIn")
    public ResponseEntity<?> activityCheckIn(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.activityCheckIn(username, activityId);
        return Tool.getResponseEntity(result);
    }

    private boolean checkParams(MultipartFile picture, JSONObject paramsJSON) throws IOException {
        if (paramsJSON.getString("activityName") == null ||
                paramsJSON.getString("introduction") == null ||
                paramsJSON.getString("type") == null ||
                paramsJSON.getString("venueId") == null ||
                paramsJSON.getString("limit")==null || paramsJSON.getIntValue("limit") <= 0 ||
                paramsJSON.getString("activityStartTime") == null ||
                paramsJSON.getString("activityEndTime") == null ||
                paramsJSON.getString("signUpStartTime") == null ||
                paramsJSON.getString("signUpEndTime") == null ||
                paramsJSON.getDate("activityStartTime").before(new Date()) ||
                paramsJSON.getDate("activityStartTime").after(paramsJSON.getDate("activityEndTime")) ||
                paramsJSON.getDate("signUpStartTime").before(new Date()) ||
                paramsJSON.getDate("signUpStartTime").after(paramsJSON.getDate("signUpEndTime")) ||
                paramsJSON.getDate("signUpEndTime").after(paramsJSON.getDate("activityStartTime"))){
            System.out.println(paramsJSON.getDate("signUpStartTime"));
            return false;
        }
        BufferedImage bi = ImageIO.read(picture.getInputStream());
        if (bi == null){
            return false;
        }
        return true;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/createActivity",produces = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<?> createActivity(@Validated @RequestPart("picture") MultipartFile picture, @Validated @RequestPart("params") String params) throws IOException {
        JSONObject paramsJSON = JSONObject.parseObject(params);
        if(!checkParams(picture, paramsJSON)) return Tool.getErrorJson("parameter error");
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.createActivity(username, picture, paramsJSON);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/deleteActivity")
    public ResponseEntity<?> deleteActivity(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.deleteActivity(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/editActivityNoPic")
    @ResponseBody
    public ResponseEntity<?> editActivityNoPic(@Validated @RequestBody EditRequest request, BindingResult bindingResult) {
        JSONObject result = Tool.DealParamError(bindingResult);
        if (result != null) {
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        Date now = new Date();
        if(request.getSignUpStartTime().after(now) && request.getSignUpEndTime().after(request.getSignUpStartTime()) && request.getActivityStartTime().after(request.getSignUpEndTime()) && request.getActivityEndTime().after(request.getActivityStartTime())) {
            String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            result = activityService.editActivityNoPic(username, request);
            return Tool.getResponseEntity(result);
        } else return Tool.getErrorJson("parameter error");
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/editActivity")
    @ResponseBody
    public ResponseEntity<?> editActivity(@Validated @RequestPart("picture") MultipartFile picture, @Validated @RequestPart("params") String params) throws IOException {
        JSONObject paramsJSON = JSONObject.parseObject(params);
        if(!checkParams(picture, paramsJSON)) return Tool.getErrorJson("parameter error");
        if(paramsJSON.getString("activityId") == null) return Tool.getErrorJson("parameter error");
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.editActivity(username, picture, paramsJSON);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/launchActivity")
    public ResponseEntity<?> launchActivity(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.launchActivity(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/hostActivityList")
    public ResponseEntity<?> hostActivityList(){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.hostActivityList(username);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/hostActivityDetails")
    public ResponseEntity<?> hostActivityDetails(@Validated @RequestParam(value = "activityId")int activityId){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.hostActivityDetails(username, activityId);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/my_info")
    public ResponseEntity<?> hostActivityDetails(){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.my_info(username);
        return Tool.getResponseEntity(result);
    }

    @PostMapping("/writeReview")
    public ResponseEntity<?> login(@Validated @RequestBody CommentRequest request, BindingResult bindingResult) {
        JSONObject result = Tool.DealParamError(bindingResult);
        if (result != null) {
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        result = activityService.comment(username, request);
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/getVenueList")
    public ResponseEntity<?> getVenueList(){
        JSONObject result = activityService.getVenueList();
        return Tool.getResponseEntity(result);
    }

    @GetMapping("/activityEnd")
    public ResponseEntity<?> activityEnd(){
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = activityService.activityEnd(username);
        return Tool.getResponseEntity(result);
    }
}
