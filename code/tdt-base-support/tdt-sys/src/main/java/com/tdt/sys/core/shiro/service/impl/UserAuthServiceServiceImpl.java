/**
 * Copyright 2018-2020 thedreamtree (https://gitee.com/thedreamtree)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdt.sys.core.shiro.service.impl;

import cn.hutool.core.convert.Convert;
import com.tdt.base.shiro.ShiroUser;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.constant.state.ManagerStatus;
import com.tdt.sys.core.shiro.ShiroKit;
import com.tdt.sys.core.shiro.service.UserAuthService;
import com.tdt.sys.modular.system.entity.User;
import com.tdt.sys.modular.system.mapper.MenuMapper;
import com.tdt.sys.modular.system.mapper.UserMapper;
import com.tdt.sys.modular.system.service.DictService;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.constant.state.ManagerStatus;
import com.tdt.sys.core.shiro.service.UserAuthService;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class UserAuthServiceServiceImpl implements UserAuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DictService dictService;

    public static UserAuthService me() {
        return SpringContextHolder.getBean(UserAuthService.class);
    }

    @Override
    public User user(String account) {

        User user = userMapper.getByAccount(account);

        // ???????????????
        if (null == user) {
            throw new CredentialsException();
        }
        // ???????????????
        if (!user.getStatus().equals(ManagerStatus.OK.getCode())) {
            throw new LockedAccountException();
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {

        ShiroUser shiroUser = ShiroKit.createShiroUser(user);

        //??????????????????
        Long[] roleArray = Convert.toLongArray(user.getRoleId());

        //????????????????????????
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        //?????????????????????????????????
        List<String> systemTypes = this.menuMapper.getMenusTypesByRoleIds(roleList);

        //??????????????????
        List<Map<String, Object>> dictsByCodes = dictService.getDictsByCodes(systemTypes);
        shiroUser.setSystemTypes(dictsByCodes);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        return menuMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public String findRoleNameByRoleId(Long roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();

        // ??????????????????
        String source = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

}
