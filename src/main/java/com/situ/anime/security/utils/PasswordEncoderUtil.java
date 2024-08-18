package com.situ.anime.security.utils;

import com.situ.anime.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author liangyunfei
 */
@Component
public class PasswordEncoderUtil implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.getDBMD5(rawPassword.toString(), MD5Util.getSalt());
    }

    /**
     *
     * @param rawPassword 编码过后的输入的密码
     * @param encodedPassword 加了一个头的编码过后的数据库中的密码
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Util.getDBMD5(rawPassword.toString(), MD5Util.getSalt()).equals(encodedPassword);
    }
}
