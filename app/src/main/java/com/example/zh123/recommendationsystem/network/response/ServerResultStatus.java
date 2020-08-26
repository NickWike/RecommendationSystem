package com.example.zh123.recommendationsystem.network.response;

/**
 * Created by zh123 on 20-3-14.
 */

public class ServerResultStatus {
    public final static int ERROR = -1;                  // 未知异常标识
    public final static int DECRYPTION_ERROR = -2;       // 解密异常
    public final static int MD5_ERROR = -3;              // MD5码生成异常
    public final static int FAILURE = -4;                // 失败 (一般用于数据的获取)
    public final static int NONE = 0;                    // 空
    public final static int OK = 1;                      // 成功标识
    public final static int NO_LOGIN = 2;                // 没有登录
    public final static int NO_SIGN_UP = 3;              // 没有注册成功
    public final static int NO_COOKIES = 4;              // 没有COOKIES信息
    public final static int INVALID_PASSWORD = 5;        // 密码不正确
    public final static int USER_EXISTED = 6;            // 用户已存在
    public final static int USER_NOT_EXISTS = 7;         // 用户不存在
}
