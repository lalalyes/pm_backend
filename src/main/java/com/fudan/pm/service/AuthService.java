package com.fudan.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fudan.pm.domain.*;
import com.fudan.pm.repository.*;
import com.fudan.pm.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LaunchActivityRepository launchActivityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public JSONObject login(String username, String password) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                String token = jwtTokenUtil.generateToken(user);
                String role = "";
                Iterator<UserRole> iterator = user.getUserRoles().iterator();
                while (iterator.hasNext()) {
                    role = iterator.next().getRole().getRoleName();
                }
                result.put("message", "success");
                result.put("token", token);
                result.put("displayName", user.getUsername());
                result.put("usrId", user.getUserId());
                result.put("role", role);
                return result;
            } else {
                result.put("message", "wrong password");
                return result;
            }
        } else {
            result.put("message", "user not found");
            return result;
        }
    }

    public JSONObject register(String username, String password, String workNumber) {
        JSONObject result = new JSONObject();
        User user1 = userRepository.findByUsername(username);
        User user2 = userRepository.findByWorkNumber(workNumber);
        if (user1 != null) {
            result.put("message", "existed username");
            return result;
        }
        if (user2 != null) {
            result.put("message", "existed work number");
            return result;
        }
        User user = new User(username, password, workNumber);
        user.setAvatar("moren.jpg");
        userRepository.save(user);
        Role role = roleRepository.findByRoleName("ROLE_user");
        UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
        result.put("message", "success");
        return result;
    }

    public JSONObject my_info(String username) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "no permission");
            return result;
        }
        result.put("message", "success");
        result.put("username", username);
        result.put("usrId", user.getUserId());
        result.put("avatar", user.getAvatar());
        result.put("introduction", user.getIntroduction());
        return result;
    }

    public JSONObject my_project(String username) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "no permission");
            return result;
        }
        System.out.println("1:--------------------------------------------------");
        List<LaunchActivity> launchActivity = launchActivityRepository.findByUserId(user.getUserId());
        //to do: 测试launchActivity是为空还是大小为0
        System.out.println("launch activity is null?:--------------------------------------------------" + (launchActivity == null));
        if (launchActivity != null) {
            System.out.println("launch activity is not null:--------------------------------------------------");
            if(launchActivity.size() == 0){
                System.out.println("launch activity size is 0:--------------------------------------------------");
            }
        }


        if (launchActivity == null || launchActivity.size() == 0) {
            result.put("message", "你还没有参与项目");
            return result;
        }
        System.out.println("2:--------------------------------------------------");


        List<Map<String, Object>> data = new ArrayList<>();
        //JASONArray re = new JASONArray();
        //修改添加项目信息
        for(int i = 0; i<launchActivity.size(); i++){
            Activity activity = activityRepository.findByActivityId(launchActivity.get(i).getActivityId());
            List<ActivityVenue> activityVenue = activity.getActivityVenues();
            //JSONObject ac = new JSONObject();
            Map<String, Object> ac = new HashMap<>();
            ac.put("activity_id", activity.getActivity_id());
            ac.put("activity_name", activity.getActivity_name());
            ac.put("introduction", activity.getIntroduction());
            ac.put("type", activity.getType());
            ac.put("picture", activity.getPicture());
            ac.put("capacity", activity.getCapacity());
            ac.put("activity_start_time", activity.getActivity_start_time());
            ac.put("activity_end_time", activity.getActivity_end_time());
            ac.put("sign_up_start_time", activity.getSign_up_start_time());
            ac.put("sign_up_end_time", activity.getSign_up_end_time());
            ac.put("launch_time", activity.getLaunch_time());
            ac.put("create_time", activity.getCreate_time());

            String location = "";
            for(int j = 0; j < activityVenue.size();j++){
                Venue venue = activityVenue.get(j).getVenue();
                location = location + venue.getCampus() + venue.getVenue_name() + "  ";
            }
            ac.put("venue", location);

            data.add(ac);
            System.out.println("activity_id:--------------------------------------------------"+launchActivity.get(i).getActivityId());
        }
        System.out.println("3:--------------------------------------------------");

        String dataString = JSON.toJSONString(data);
        result.put("data", dataString);
        return result;
    }

    //获取所有项目
    public JSONObject all_project(String username) {
        JSONObject result = new JSONObject();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            result.put("message", "no permission");
            return result;
        }
        System.out.println("1:--------------------------------------------------");
        Iterator<Activity> activities = activityRepository.findAll().iterator();
        System.out.println("2:--------------------------------------------------");


        List<Map<String, Object>> data = new ArrayList<>();
        //JASONArray re = new JASONArray();
        //修改添加项目信息
        while(activities.hasNext()){

            Activity activity = activities.next();
            List<ActivityVenue> activityVenue = activity.getActivityVenues();
            //JSONObject ac = new JSONObject();
            Map<String, Object> ac = new HashMap<>();
            ac.put("activity_id", activity.getActivity_id());
            ac.put("activity_name", activity.getActivity_name());
            ac.put("introduction", activity.getIntroduction());
            ac.put("type", activity.getType());
            ac.put("picture", activity.getPicture());
            ac.put("capacity", activity.getCapacity());
            ac.put("activity_start_time", activity.getActivity_start_time());
            ac.put("activity_end_time", activity.getActivity_end_time());
            ac.put("sign_up_start_time", activity.getSign_up_start_time());
            ac.put("sign_up_end_time", activity.getSign_up_end_time());
            ac.put("launch_time", activity.getLaunch_time());
            ac.put("create_time", activity.getCreate_time());

            String location = "";
            for(int j = 0; j < activityVenue.size();j++){
                Venue venue = activityVenue.get(j).getVenue();
                location = location + venue.getCampus() + venue.getVenue_name() + "  ";
            }
            ac.put("venue", location);

            data.add(ac);
            System.out.println("activity_id:--------------------------------------------------"+activity.getActivity_id());
        }
        System.out.println("3:--------------------------------------------------");

        String dataString = JSON.toJSONString(data);
        result.put("data", dataString);
        return result;
    }
}
