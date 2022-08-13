package com.newland.srb.core.controller.admin;

import com.newland.srb.common.result.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: leell
 * Date: 2022/8/12 22:38:22
 */
@Api(tags = "后台用户")
@RestController
@RequestMapping("/admin/core/user")
public class AdminController {

    @PostMapping("/login")
    public Map<String, Object> login() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 20000);
        map.put("data", new HashMap<>() {{
            put("token", "admin-token");
        }});
        return map;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", new String[]{"admin"});
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("code", 20000);
        map2.put("data", map);
        return map2;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> map2 = new HashMap<>();
        map2.put("code", 20000);
        map2.put("data", "success");
        return map2;
    }

}
