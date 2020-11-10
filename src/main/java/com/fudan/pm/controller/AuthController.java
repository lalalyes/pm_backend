package com.fudan.pm.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fudan.pm.Tool;
import com.fudan.pm.controller.request.LoginRequest;
import com.fudan.pm.controller.request.RegisterRequest;
import com.fudan.pm.domain.User;
import com.fudan.pm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@RestController
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Validated
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/welcome", produces = "application/json")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "This is our pm project";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, BindingResult bindingResult) {
        JSONObject result = Tool.DealParamError(bindingResult);
        if (result != null) {
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        result = authService.login(request.getUsername(), request.getPassword());
        return Tool.getResponseEntity(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request, BindingResult bindingResult) {
        JSONObject result = Tool.DealParamError(bindingResult);
        if (result != null) {
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        result = authService.register(request.getUsername(), request.getPassword(), request.getWorkNumber());
        return Tool.getResponseEntity(result);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/my_info",produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> my_info() {
        System.out.println("进来了--------------------------------------------------:");
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = authService.my_info(username);
        return Tool.getResponseEntity(result);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/my_project",produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> my_project() {
        /*
        to do: 获取项目信息，不要动我这一部分，我后面会改
         */
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        JSONObject result = authService.my_info(username);
        return Tool.getResponseEntity(result);
    }
}
