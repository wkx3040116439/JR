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

import java.time.LocalDate;
import java.time.Period;


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
    public WebResult login(String phone, String password) {
        return userService.UserLogin(phone,password);
    }

    /*
     * 用户登出
     * */
    @PostMapping("/userLogout")
    @ResponseBody
    public WebResult loginout() {
        WebResult webResult = new WebResult();
        try {
            if (StpUtil.isLogin()) {
                // Perform logout and session cleanup
                StpUtil.logout();
                webResult.setCode(1);
                webResult.setMessage("退出登录成功");
            } else {
                webResult.setCode(0);
                webResult.setMessage("账户未登录");
            }
        } catch (Exception e) {
            webResult.setCode(-1);
            e.printStackTrace();
        }
        return webResult;
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
    @PostMapping("/useRespwd")
    @ResponseBody
    public WebResult updatePwd(String phone, String password,String province,String city) {
        return userService.updatePwd(phone,password,province,city);
    }
    /*
     * 用户信息接口
     * */
        @PostMapping("/getUserData")
    @ResponseBody
    public WebResult getUserdata(String phone) {
        WebResult webResult = new WebResult();
        if(StpUtil.isLogin()){
           User user = userMapper.getUserByUser(phone);
           webResult.setCode(1);
           webResult.setData(user);
        }else {
            webResult.setCode(0);
            webResult.error("用户未登录");
        }
        return webResult;
    }
    /*
     * 修改用户信息
     * */
    @PostMapping("/userUpdate")
    @ResponseBody
    public WebResult updateUser(User user) {
        WebResult webResult = new WebResult();
        try {
            System.out.println(user);
            LocalDate birthday = user.getBirthday(); //获取生日
            System.out.println("生日"+birthday);
            LocalDate currentDate = LocalDate.now(); //获取当前时间
            System.out.println("现在时间"+birthday);
            Period age = Period.between(birthday, currentDate); //算出年龄
            user.setAge(age.getYears()); //插入年龄
            user.setBirthday(birthday); //插入生日
            userMapper.updateUser(user);
            webResult.setCode(1);
        }catch (Exception e){
            e.printStackTrace();
            webResult.setCode(0);
            webResult.error("访问数据库出错");
        }

        return webResult;
    }
    /*
     * 删除用户
     * */
    @PostMapping("/userDelete")
    @ResponseBody
    public WebResult deleteUser(String phone) {
        WebResult webResult = new WebResult();
        userMapper.deleteUser(phone);
        return webResult;
    }


    // 上传用户头像接口
    @PostMapping("/uploadUserImg")
    public ResponseEntity uploadUserImg(@RequestParam("file") MultipartFile file, @RequestParam("phone") String phone) {
        try {
            String uploadResult = minIOService.uploadUserimg(file, phone);
            if (uploadResult.startsWith("http")) {
                return ResponseEntity.ok(uploadResult);
            } else {
                return ResponseEntity.ok(uploadResult);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 上传用户头像接口
    @PostMapping("/uploadUserBg")
    public ResponseEntity uploadUserBg(@RequestParam("file") MultipartFile file, @RequestParam("phone") String phone) {
        try {
            String uploadResult = minIOService.uploadUserbg(file, phone);
            if (uploadResult.startsWith("http")) {
                return ResponseEntity.ok(uploadResult);
            } else {
                return ResponseEntity.ok(uploadResult);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
