package com.tdt.sys.modular.third.factory;

import com.tdt.base.consts.ConstantsContext;
import com.tdt.sys.core.constant.state.ManagerStatus;
import com.tdt.sys.core.shiro.ShiroKit;
import com.tdt.sys.modular.system.entity.User;
import com.tdt.sys.modular.third.entity.OauthUserInfo;
import cn.stylefeng.roses.core.util.ToolUtil;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.model.AuthUser;

import java.util.Date;

/**
 * oauth绑定记录
 *
 * @author gcj
 * @Date 2019/6/9 19:02
 */
public class OAuthUserInfoFactory {

    /**
     * 创建oauth绑定
     *
     * @author gcj
     * @Date 2019/6/9 19:03
     */
    public static OauthUserInfo createOAuthUserInfo(Long userId, AuthUser oauthUser) {
        OauthUserInfo oauthUserInfo = new OauthUserInfo();

        ToolUtil.copyProperties(oauthUser, oauthUserInfo);
        //设置openId和第三方源
        oauthUserInfo.setUuid(oauthUser.getUuid());
        oauthUserInfo.setSource(oauthUser.getSource());

        //设置本系统地用户id
        oauthUserInfo.setUserId(userId);

        return oauthUserInfo;
    }

    /**
     * 创建第三方应用在本应用的用户
     *
     * @author gcj
     * @Date 2019/6/9 19:11
     */
    public static User createOAuthUser(AuthUser authUser) {

        User systemUser = new User();

        //设置密码，利用token
        String salt = ShiroKit.getRandomSalt(5);
        String password = ShiroKit.md5(String.valueOf(authUser.getToken()), salt);
        systemUser.setPassword(password);
        systemUser.setSalt(salt);

        //利用openId设置账号
        systemUser.setAccount(ConstantsContext.getOAuth2UserPrefix() + "_" + authUser.getSource() + "_" + authUser.getUsername());
        systemUser.setName(authUser.getNickname());
        systemUser.setBirthday(new Date());
        systemUser.setSex(AuthUserGender.MALE.equals(authUser.getGender()) ? "M" : "F");
        systemUser.setEmail("未设置");
        systemUser.setPhone("未设置");

        //固定第三方应用的角色和部门
        systemUser.setRoleId("5");
        systemUser.setDeptId(25L);

        systemUser.setStatus(ManagerStatus.OK.getCode());
        systemUser.setCreateTime(new Date());
        systemUser.setCreateUser(1L);
        systemUser.setUpdateTime(new Date());
        systemUser.setUpdateUser(1L);
        systemUser.setVersion(0);

        return systemUser;
    }

}
