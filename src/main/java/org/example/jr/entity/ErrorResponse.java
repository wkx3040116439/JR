package org.example.jr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xingxing
 * @date 2024/7/24 16:50:07
 * @apiNote
 */
@Data               // lombok注解，自动生成getter、setter方法
@AllArgsConstructor // lombok注解，自动生成全参构造方法
@NoArgsConstructor  // lombok注解，自动生成无参构造方法
public class ErrorResponse {
    private int code;
    private String message;
}
