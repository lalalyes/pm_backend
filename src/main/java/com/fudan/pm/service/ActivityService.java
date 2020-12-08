package com.fudan.pm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudan.pm.domain.*;
import com.fudan.pm.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
                List<ActivityVenue> activityVenues = activityVenueRepository.findByVenueId(venue.getVenue_id());
                for(ActivityVenue activityVenue : activityVenues) {
                    activities.add(activityRepository.findByActivityId(activityVenue.getActivityId()));
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
        if (activity == null) {
            result.put("message", "failure");
            return result;
        }

        result.put("activityName",activity.getActivity_name());
        result.put("introduction",activity.getIntroduction());
        result.put("type",activity.getType());
        result.put("capacity",activity.getCapacity());
        result.put("picture",activity.getPicture());
        ActivityVenue av = activityVenueRepository.findByActivityId(activity.getActivity_id());
        Venue venue = venueRepository.findByVenueId(av.getVenueId());
        result.put("activityVenue",venue.getCampus() + venue.getVenue_name());
        result.put("activityStartTime",activity.getActivity_start_time());
        result.put("activityEndTime",activity.getActivity_end_time());
        result.put("signUpStartTime",activity.getSign_up_start_time());
        result.put("signUpEndTime",activity.getSign_up_end_time());
        result.put("enrolled",participateRepository.findByUserIdAndActivityId(user.getUserId(), activityId) != null);
        result.put("currentNumber", participateRepository.getActivityCurrentNumber(activityId));

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

    public JSONObject activityEnrollment(String username,int activityId){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        Activity activity = activityRepository.findByActivityId(activityId);
        if (activity == null) {
            result.put("message", "activity not exist");
            return result;
        }

        if(activity.getSign_up_start_time().after(new Date()) || activity.getSign_up_end_time().before(new Date())) {
            result.put("message", "sign up is close");
            return result;
        }

        Participate participate = participateRepository.findByUserIdAndActivityId(user.getUserId(), activityId);
        if(participate != null) {
            result.put("message", "you had participated it");
            return result;
        }

        Participate p = new Participate(user.getUserId(), activityId);
        participateRepository.save(p);
        result.put("message", "success");
        return result;
    }

    public JSONObject retreatEnrollment(String username,int activityId){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        Activity activity = activityRepository.findByActivityId(activityId);
        if (activity == null) {
            result.put("message", "activity not exist");
            return result;
        }


        Participate participate = participateRepository.findByUserIdAndActivityId(user.getUserId(), activityId);
        if(participate == null) {
            result.put("message", "not sign up for this activity");
            return result;
        }
        participateRepository.delete(participate);
        result.put("message", "success");
        return result;
    }

    public JSONObject activityCheckIn(String username, int activityId){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        Activity activity = activityRepository.findByActivityId(activityId);
        if (activity == null) {
            result.put("message", "activity not exist");
            return result;
        }
        Participate participate = participateRepository.findByUserIdAndActivityId(user.getUserId(), activityId);
        if(participate == null) {
            result.put("message", "not sign up for this activity");
            return result;
        }

        if(participate.getPresent() == 1) {
            result.put("message", "you have checked in");
            return result;
        }
        participateRepository.updatePresent(user.getUserId(), activityId);
        result.put("message", "success");
        return result;
    }

    public String saveFile(MultipartFile file){
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        String saveDir;
        saveDir = "/var/www/html/images/";
//        saveDir = "D:\\Documents\\AD web\\pj\\courseImages\\";
        File serverFile = new File(saveDir + filename);

        if(!serverFile.exists()) {
            //先得到文件的上级目录，并创建上级目录，在创建文件
            serverFile.getParentFile().mkdir();
            try {
                //创建文件
                serverFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将上传的文件写入到服务器端文件内
        try {
            file.transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public JSONObject createActivity(String username, MultipartFile picture, JSONObject paramsJSON){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        Venue venue = venueRepository.findByVenueId(paramsJSON.getIntValue("venueId"));
        if(venue == null) {
            result.put("message", "venue not exist");
            return result;
        }
        String name = saveFile(picture);
        Activity activity = new Activity(paramsJSON.getString("activityName"), paramsJSON.getString("introduction"),
                paramsJSON.getString("type"), name,
                paramsJSON.getIntValue("limit"), paramsJSON.getDate("activityStartTime"),
                paramsJSON.getDate("activityEndTime"), paramsJSON.getDate("signUpStartTime"),
                paramsJSON.getDate("signUpEndTime"));
        activityRepository.save(activity);
        ActivityVenue av = new ActivityVenue(activity.getActivity_id(), paramsJSON.getIntValue("venueId"));
        activityVenueRepository.save(av);
        LaunchActivity la = new LaunchActivity(user.getUserId(), activity.getActivity_id());
        launchActivityRepository.save(la);
        result.put("message", "success");
        return result;
    }

    public JSONObject deleteActivity(String username, int activityId){
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        LaunchActivity la = launchActivityRepository.findByActivityIdAndUserId(activityId, user.getUserId());
        if (la == null) {
            result.put("message", "the activity is not yours");
            return result;
        }
        launchActivityRepository.deleteByActivityId(activityId);
        activityVenueRepository.deleteByActivityId(activityId);
        participateRepository.deleteByActivityId(activityId);
        activityRepository.deleteByActivityId(activityId);
        result.put("message", "success");
        return result;
    }
}