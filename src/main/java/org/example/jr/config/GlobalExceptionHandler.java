package org.example.jr.config;

/**
 * @author xingxing
 * @date 2024/7/24 16:50:07
 * @apiNote
 */
import cn.dev33.satoken.exception.NotLoginException;
import org.springframework.http.HttpStatus;
import org.example.jr.entity.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public ResponseEntity<Object> handleNotLoginException(NotLoginException e) {
        // 创建自定义的错误响应
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(0);
        errorResponse.setMessage("请登录后再进行此操作。");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
