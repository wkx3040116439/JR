//
//
//import cn.dev33.satoken.exception.NotLoginException;
//import cn.dev33.satoken.util.SaResult;
//import org.example.jr.entity.WebResult;
//import org.springframework.web.bind.annotation.ExceptionHandler; /**
// * @author xingxing
// * @date 2024/7/25 09:53:07
// * @apiNote
// */
//// 全局异常拦截（拦截项目中的NotLoginException异常）
//@ExceptionHandler(NotLoginException.class)
//public WebResult handlerNotLoginException(NotLoginException nle)
//        throws Exception {
//    WebResult  webResult = new WebResult();
//    // 打印堆栈，以供调试
//
//    // 判断场景值，定制化异常信息
//    String message = "";
//    if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
//        message = "未能读取到有效 token";
//    }
//    else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
//        message = "token 无效";
//    }
//    else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
//        message = "token 已过期";
//    }
//    else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
//        message = "token 已被顶下线";
//    }
//    else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
//        message = "token 已被踢下线";
//    }else {
//        message = "当前会话未登录";
//    }
//    webResult.error(message);
//    // 返回给前端
//    return webResult;
//}
//
//public void main() {
//}
