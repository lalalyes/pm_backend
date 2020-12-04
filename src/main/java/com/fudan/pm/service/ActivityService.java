package com.fudan.pm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudan.pm.domain.*;
import com.fudan.pm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private ActivityVenueRepository activityVenueRepository;
    @Autowired
    private ParticipateRepository participateRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private LaunchActivityRepository launchActivityRepository;

    public JSONObject searchActivity(String content, int type) {
        JSONObject result = new JSONObject();
        List<Activity> activities = new ArrayList<>();
        if(type == 0) {
            activities = activityRepository.findByActivityName(content);
        } else {
            List<Venue> venues = venueRepository.findByVenueName(content);
            for(Venue venue : venues) {
                List<ActivityVenue> activityVenues = activityVenueRepository.findAllByVenue(venue);
                for(ActivityVenue activityVenue : activityVenues) {
                    activities.add(activityRepository.findByActivityId(activityVenue.getActivity_id()));
                }
            }
        }
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

    public JSONObject activityList (String username){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "failure");
            return result;
        }

        List<Activity> activities = activityRepository.findAll(user.getUserId());
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
        JSONArray activityArray = new JSONArray();
        List<Participate> participates = participateRepository.findByUserId(user.getUserId());
        System.out.println(participates);
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
        result.put("capacity",activity.getCapacity());
        result.put("picture",activity.getPicture());
        result.put("activityVenue",activity.getActivityVenues());
        result.put("activityStartTime",activity.getActivity_start_time());
        result.put("activityEndTime",activity.getActivity_end_time());
        result.put("signUpStartTime",activity.getSign_up_start_time());
        result.put("signUpEndTime",activity.getSign_up_end_time());
        result.put("enrolled",participateRepository.findByUserIdAndActivityId(user.getUserId(), activityId) != null);

        JSONArray jsonComments = new JSONArray();
        List<Participate> participates = participateRepository.findCommentsByActivityId(activityId);
        for(Participate participate : participates) {
            JSONObject object = new JSONObject();
            object.put("userId", participate.getUser_id());
            object.put("username", userRepository.findByUserId(participate.getUser_id()).getUsername());
            object.put("content", participate.getComment());
            object.put("score", participate.getScore());
            object.put("picture", participate.getPicture());
            jsonComments.add(object);
        }
        result.put("comments", jsonComments);

        JSONObject host = new JSONObject();
        LaunchActivity launchActivity = launchActivityRepository.findByActivityId(activityId);
        host.put("userId", launchActivity.getUserId());
        host.put("username", userRepository.findByUserId(launchActivity.getUserId()).getUsername());
        result.put("host", host);
        return result;
    }
}
