package org.example.jr.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.example.jr.entity.User;
import org.example.jr.entity.WebResult;
import org.example.jr.mapper.UserMapper;
import org.example.jr.service.UserService;
import org.example.jr.service.lmpl.MinIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/JR")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MinIOService minIOService;

    @Autowired
    private UserMapper userMapper;

    /*
     * 用户登录
     * */
    @PostMapping("/userLogin")
    @ResponseBody
    public WebResult login(Long phone, String password) {
        return userService.UserLogin(phone,password);
    }

    /*
     * 用户登出
     * */
    @PostMapping("/userlogout")
    @ResponseBody
    public boolean updatePwd(Long phone) {
         if(!StpUtil.isLogin()){
             // 退出当前会话
             StpUtil.logout(phone);
             return true;
         }else {
             // 当前用户未登录
             return false;
         }
    }
    /*
    * 用户注册
    * */
    @PostMapping("/userRegister")
    @ResponseBody
    public WebResult register(User user) {
        return userService.UserRegister(user);
    }
    /*
     * 修改用户密码
     * */
    @PostMapping("/userUpdatepwd")
    @ResponseBody
    public WebResult updatePwd(Long phone, String password,String province,String city) {
        return userService.updatePwd(phone,password,province,city);
    }

    /*
     * 修改用户信息
     * */
    @PostMapping("/userUpdate")
    @ResponseBody
    public WebResult updateUser(User user) {
        WebResult webResult = new WebResult();
        userMapper.updateUser(user);
        return webResult;
    }

    @PostMapping("/userDelete")
    @ResponseBody
    public WebResult deleteUser(Long phone) {
        WebResult webResult = new WebResult();
        userMapper.deleteUser(phone);
        return webResult;
    }


    // 上传用户头像接口
    @PostMapping("/uploadUserAvatar")
    public ResponseEntity uploadUserAvatar(@RequestParam("file") MultipartFile file, @RequestParam("phone") Long phone) {
        try {
            String uploadResult = minIOService.uploadUserAvatar(file, phone);
            if (uploadResult.startsWith("http")) {
                return ResponseEntity.ok(uploadResult);
            } else {
                return ResponseEntity.ok(uploadResult);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    // 获取用户头像接口
    @GetMapping("/getUserAvatar")
    public ResponseEntity getUserAvatar(@RequestParam("phone")Long phone) {
        try {
            String avatarUrl = minIOService.getUserAvatarUrl(phone);
            return ResponseEntity.ok(avatarUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
