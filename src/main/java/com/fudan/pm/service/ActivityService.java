package com.fudan.pm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudan.pm.domain.Activity;
import com.fudan.pm.domain.Participate;
import com.fudan.pm.domain.User;
import com.fudan.pm.repository.ActivityRepository;
import com.fudan.pm.repository.ParticipateRepository;
import com.fudan.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("All")
@Transactional
@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipateRepository participateRepository;

    public JSONObject activityList (String username){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "failure");
            return result;
        }

        List<Activity> activities = activityRepository.findAll();
        JSONArray activityArray = new JSONArray();
        for (Activity activity:activities){
            JSONObject jsonActivity = new JSONObject();
            jsonActivity.put("activityName",activity.getActivity_name());
            jsonActivity.put("activityId",activity.getActivity_id());
            jsonActivity.put("introduction",activity.getIntroduction());
            jsonActivity.put("type",activity.getType());
            jsonActivity.put("picture",activity.getPicture());
            activityArray.add(jsonActivity);
        }
        result.put("activities",activityArray);
        return result;
    }

    public JSONObject enrolledActivity(String username){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "failure");
            return result;
        }

        JSONArray activityArray = new JSONArray();
        List<Participate> participates = participateRepository.findByUserId(user.getUserId());
        for (Participate participate:participates){
            Activity activity = activityRepository.findByActivityId(participate.getActivity_id());
            JSONObject jsonActivity = new JSONObject();
            jsonActivity.put("activityName",activity.getActivity_name());
            jsonActivity.put("activityId",activity.getActivity_id());
            jsonActivity.put("introduction",activity.getIntroduction());
            jsonActivity.put("type",activity.getType());
            jsonActivity.put("picture",activity.getPicture());
            activityArray.add(jsonActivity);
        }
        result.put("activities",activityArray);
        return result;
    }

    public JSONObject activityDetails(String username,int activityId){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        Activity activity = activityRepository.findByActivityId(activityId);
        if (user == null || activity == null) {
            result.put("message", "failure");
            return result;
        }

        result.put("activityName",activity.getActivity_name());
        result.put("introduction",activity.getIntroduction());
        result.put("type",activity.getType());
        result.put("picture",activity.getPicture());
        result.put("activityVenue",activity.getActivityVenues());
        result.put("activityStartTime",activity.getActivity_start_time());
        result.put("activityEndTime",activity.getActivity_end_time());
        result.put("signUpStartTime",activity.getSign_up_start_time());
        result.put("signUpEndTime",activity.getSign_up_end_time());

        //这里好像没写完
        return result;
    }
}
