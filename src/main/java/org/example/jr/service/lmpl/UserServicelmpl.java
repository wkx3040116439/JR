package org.example.jr.service.lmpl;

import cn.dev33.satoken.session.SaSession;
import org.example.jr.entity.User;
import org.example.jr.entity.WebResult;
import org.example.jr.mapper.UserMapper;
import org.example.jr.service.UserService;
import org.example.jr.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.dev33.satoken.stp.StpUtil;
import java.time.LocalDate;
import java.time.Period;


@Service
public class UserServicelmpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public WebResult UserLogin(String phone, String password) {
        WebResult webResult = new WebResult();
        try {
            User findUser = userMapper.getUserByUser(phone);
            if (findUser.getPhone() == null) {
                webResult.setCode(0);
                webResult.error("账号错误");
            } else {
                if (PasswordEncoder.matches(password, findUser.getPassword())) {
                    // 更新用户登录时间
                    if(StpUtil.isLogin()){
                        userMapper.updateLogintime(phone);
                        webResult.setCode(1);
                        webResult.setMessage("用户已登录");
                        StpUtil.updateLastActivityToNow();
                    }else{
                        String userId = findUser.getPhone();
                        StpUtil.login(userId);
                        userMapper.updateLogintime(phone);
                        webResult.setCode(1);
                        webResult.setMessage("登陆成功");
                        StpUtil.getSession().set("user" ,findUser);
                        webResult.setToken(StpUtil.getTokenValue());
                    }

                } else {
                    webResult.setCode(0);
                    webResult.setMessage("密码错误");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            webResult.error("访问数据库出错");
        }
        return webResult;
    }


    @Override
    public WebResult UserRegister(User user) {
        WebResult webResult = new WebResult();
        try {
            User findUser = userMapper.getUserByUser(user.getPhone());
            if (findUser != null) {
                webResult.error("该账号已被注册");
            } else {
                LocalDate birthday = user.getBirthday(); //获取生日
                LocalDate currentDate = LocalDate.now(); //获取当前时间
                Period age = Period.between(birthday, currentDate); //算出年龄

                user.setPassword(PasswordEncoder.encode(user.getPassword()));
                user.setAge(age.getYears());
                user.setBirthday(birthday);

                userMapper.insertUser(user);
                webResult.setCode(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            webResult.error("访问数据库出错");
        }

        return webResult;
    }

    //更新用户密码
    @Override
    public WebResult updatePwd(String phone, String password,String province,String city) {
        WebResult webResult = new WebResult();
        System.out.println(province+city);
        try {
            User findUser = userMapper.getUserByUser(phone);
            if(findUser == null){
                webResult.setCode(-1);
                webResult.error("账号不存在");
            }else {
                if (province.equals(findUser.getProvince()) && city.equals(findUser.getCity())){
                    String Newpassword = PasswordEncoder.encode(password);
                    userMapper.updatePwd(phone,Newpassword);
                    webResult.setCode(1);
                    webResult.setMessage("密码重置成功");
                }else {
                    webResult.error("账号注册地信息错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            webResult.error("访问数据库出错");
        }
        return webResult;
    }
    //删除用户
    @Override
    public WebResult deleteUser(String phone) {
        return null;
    }


}
