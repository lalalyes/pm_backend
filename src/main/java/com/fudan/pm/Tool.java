package com.fudan.pm;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Tool {
    private static Logger logger = Logger.getLogger("LoggingDemo");

    private Random rand = SecureRandom.getInstanceStrong();

    public Tool() throws NoSuchAlgorithmException {
    }

    public static JSONObject DealParamError(BindingResult bindingResult) throws JSONException {
        if (bindingResult.hasErrors()) {
            //有校验没通过
            JSONObject result = new JSONObject();
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                System.out.println(error.getDefaultMessage());  //输出具体的错误信息
            }
            result.put("message", "parameter error");
            return result;
        }
        //通过了参数校验
        return null;
    }

    public static ResponseEntity<?> getResponseEntity(JSONObject result) throws JSONException {
        if (result.getString("message") != null && !"success".equals(result.getString("message"))) {
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.toJSONString(), HttpStatus.OK);
    }
}
