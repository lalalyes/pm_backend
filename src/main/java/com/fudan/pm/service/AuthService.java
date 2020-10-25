package com.fudan.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.fudan.pm.domain.Role;
import com.fudan.pm.domain.User;
import com.fudan.pm.domain.UserRole;
import com.fudan.pm.repository.RoleRepository;
import com.fudan.pm.repository.UserRepository;
import com.fudan.pm.repository.UserRoleRepository;
import com.fudan.pm.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

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
}
