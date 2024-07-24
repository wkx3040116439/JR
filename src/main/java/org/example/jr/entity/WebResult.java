package org.example.jr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xingxing
 * @date 2024/7/9 18:22:07
 * @apiNote
 */
@Data               // lombok注解，自动生成getter、setter方法
@AllArgsConstructor // lombok注解，自动生成全参构造方法
@NoArgsConstructor  // lombok注解，自动生成无参构造方法
@ToString           // lombok注解，自动生成toString方法
public class WebResult {
    private int code;
    private String message;
    private String token;
    private Object data;


    public void success(String message,String token) {
        this.code = 1;
        this.message = message;
        this.token = token;
    }

    public void error(String message) {
        this.code = 0;
        this.message = message;
    }
}
