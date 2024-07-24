package org.example.jr.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

@Data               // lombok注解，自动生成getter、setter方法
@AllArgsConstructor // lombok注解，自动生成全参构造方法
@NoArgsConstructor  // lombok注解，自动生成无参构造方法
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nick_name;
    private String user_name;
    private Integer age;
    private String sex;
    private String province;
    private String city;
    private Long phone;
    private LocalDate birthday;
    private String constellation;
    private String email;
    private Long account;
    private String password;
    private LocalDateTime last_login_time;
    private LocalDateTime register_time;
    private LocalDateTime update_time;
    private String imgurl;
}