package org.example.jr.service;

import org.example.jr.entity.User;
import org.example.jr.entity.WebResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //用户登录业务接口
    WebResult UserLogin(Long phone, String password);

    //用户注册业务接口
    WebResult UserRegister(User user);

    //更新用户密码
    WebResult updatePwd(Long phone, String password,String province,String city);

    //用户删除业务接口
    WebResult deleteUser(Long phone);
}
