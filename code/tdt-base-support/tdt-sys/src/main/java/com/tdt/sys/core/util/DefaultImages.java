
package com.tdt.sys.core.util;

import com.tdt.sys.core.listener.ConfigListener;
import com.tdt.sys.core.shiro.ShiroKit;
import com.tdt.sys.core.listener.ConfigListener;
import com.tdt.sys.core.shiro.ShiroKit;

/**
 * 获取默认图片地址
 *
 * @author gcj
 * @date 2018-10-30-5:50 PM
 */
public class DefaultImages {

    /**
     * 默认的登录页面背景
     *
     * @author gcj
     * @Date 2018/10/30 5:51 PM
     */
    public static String loginBg() {
        return ConfigListener.getConf().get("contextPath") + "/assets/common/images/login-register.jpg";
    }

    /**
     * 默认的用户图片的base64编码
     *
     * @author gcj
     * @Date 2018/10/30 5:51 PM
     */
    public static String defaultAvatarUrl() {
        if (ShiroKit.oauth2Flag()) {
            return ConfigListener.getConf().get("contextPath") + "/oauth/avatar";
        } else {
            return ConfigListener.getConf().get("contextPath") + "/system/previewAvatar";
        }
    }

    /**
     * 默认的404错误页面背景
     *
     * @author gcj
     * @Date 2018/10/30 5:51 PM
     */
    public static String error404() {
        return ConfigListener.getConf().get("contextPath") + "/assets/common/images/error-bg.jpg";
    }
}
