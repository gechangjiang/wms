package com.tdt.sys.modular.third.service;

import me.zhyd.oauth.model.AuthUser;

/**
 * 第三方登录服务
 *
 * @author www.qiqucode.com
 * @Date 2019/6/9 17:53
 */
public interface LoginService {

    /**
     * 第三方登录
     *
     * @author www.qiqucode.com
     * @Date 2019/6/9 18:21
     */
    String oauthLogin(AuthUser oauthUser);
}
