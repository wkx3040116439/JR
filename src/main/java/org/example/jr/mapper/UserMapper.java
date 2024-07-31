package org.example.jr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.jr.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.example.jr.entity.WebResult;
import org.springframework.stereotype.Repository;

@Mapper // 这里使用Mapper注解，表示这是一个mybatis的mapper接口
@Repository
public interface UserMapper extends BaseMapper<User> {
    //获取用户信息
    User getUserByUser(String phone);
    //插入用户信息
    void insertUser(User user);

    //更新用户信息
    void updateUser(User user);
    //插入用户头像
    void userImg(String phone,String imgurl);
    //删除用户信息
    void deleteUser(String phone);

    //更新用户登录时间
    void updateLogintime(String phone);

    //更新用户密码66666
    void updatePwd(String phone,String password);
}
