package com.systemtraining.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.systemtraining.common.HttpResponseEntity;
import com.systemtraining.entity.User;
import com.systemtraining.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // Lombok将生成一个构造函数，自动注入所有final字段
public class UserController {
    private final UserService userService;

    @PostMapping("/userLogin")
    public HttpResponseEntity userLogin(@RequestBody User user, HttpServletResponse response) {
        List<User> userList = userService.query()
                .eq("username", user.getUsername())
                .eq("password", user.getPassword())
                .eq("status", "1").list();
        if (userList.isEmpty()) {
            return HttpResponseEntity.response(false, "登录", null);
        } else {
            User loggedInUser = userList.get(0);
            Cookie cookie = new Cookie("username", loggedInUser.getUsername());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return HttpResponseEntity.response(true, "登录", userList);
        }
    }

    @PostMapping("/addUserInfo")
    public HttpResponseEntity addUserinfo(@RequestBody User user) {
        boolean success = userService.save(user);
        return HttpResponseEntity.response(success, "创建", null);
    }

    @PostMapping("/modifyUserInfo")
    public HttpResponseEntity modifyUserinfo(@RequestBody User user) {
        boolean success = userService.updateById(user);
        return HttpResponseEntity.response(success, "修改", null);
    }

    @PostMapping("/deleteUserinfo")
    public HttpResponseEntity deleteUserById(@RequestBody User user) {
        boolean success = userService.removeById(user);
        return HttpResponseEntity.response(success, "删除", null);
    }

    @PostMapping("/queryUserList")
    public HttpResponseEntity queryUserList(@RequestBody Map<String, Object> map) {
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        Page<User> page = new Page<>(pageNum, pageSize);
        userService.query().eq("status", "1")
                .like("username", map.get("username")).page(page);
        List<User> userList = page.getRecords();
        boolean success = !userList.isEmpty();
        return HttpResponseEntity.response(success, "查询", userList);
    }

}
