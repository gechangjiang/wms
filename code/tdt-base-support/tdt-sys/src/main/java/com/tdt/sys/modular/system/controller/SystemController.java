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
package com.tdt.sys.modular.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.tdt.base.oshi.SystemHardwareInfo;
import com.tdt.base.shiro.ShiroUser;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.log.LogObjectHolder;
import com.tdt.sys.core.shiro.ShiroKit;
import com.tdt.sys.modular.system.entity.Notice;
import com.tdt.sys.modular.system.entity.User;
import com.tdt.sys.modular.system.service.FileInfoService;
import com.tdt.sys.modular.system.service.NoticeService;
import com.tdt.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.log.LogObjectHolder;
import com.tdt.sys.core.shiro.ShiroKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

/**
 * ???????????????
 *
 * @author gcj
 * @Date 2017???2???17???20:27:22
 */
@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private NoticeService noticeService;

    /**
     * ???????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/console")
    public String console() {
        return "/modular/frame/console.html";
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/console2")
    public String console2() {
        return "/modular/frame/console2.html";
    }

    /**
     * ????????????????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/systemInfo")
    public String systemInfo(Model model) {

        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();

        model.addAttribute("server", systemHardwareInfo);

        return "/modular/frame/systemInfo.html";
    }

    /**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/notice")
    public String hello() {
        List<Notice> notices = noticeService.list();
        super.setAttr("noticeList", notices);
        return "/modular/frame/notice.html";
    }

    /**
     * ?????????
     *
     * @author gcj
     * @Date 2019/1/24 3:38 PM
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return "/modular/frame/welcome.html";
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2019/1/24 3:38 PM
     */
    @RequestMapping("/theme")
    public String theme() {
        return "/modular/frame/theme.html";
    }

    /**
     * ???????????????????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return "/modular/frame/password.html";
    }

    /**
     * ??????????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/message")
    public String message() {
        return "/modular/frame/message.html";
    }

    /**
     * ?????????????????????????????????
     *
     * @author gcj
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = this.userService.getById(userId);

        model.addAllAttributes(BeanUtil.beanToMap(user));
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));
        LogObjectHolder.me().set(user);
        return "/modular/frame/user_info.html";
    }

    /**
     * ???????????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/commonTree")
    public String deptTreeList(@RequestParam("formName") String formName,
                               @RequestParam("formId") String formId,
                               @RequestParam("treeUrl") String treeUrl, Model model) {

        if (ToolUtil.isOneEmpty(formName, formId, treeUrl)) {
            throw new RequestEmptyException("????????????????????????");
        }

        try {
            model.addAttribute("formName", URLDecoder.decode(formName, "UTF-8"));
            model.addAttribute("formId", URLDecoder.decode(formId, "UTF-8"));
            model.addAttribute("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RequestEmptyException("????????????????????????");
        }

        return "/common/tree_dlg.html";
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/updateAvatar")
    @ResponseBody
    public Object uploadAvatar(@RequestParam("fileId") String fileId) {

        if (ToolUtil.isEmpty(fileId)) {
            throw new RequestEmptyException("??????????????????");
        }

        fileInfoService.updateAvatar(fileId);

        return SUCCESS_TIP;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/11/9 12:45 PM
     */
    @RequestMapping("/previewAvatar")
    @ResponseBody
    public Object previewAvatar(HttpServletResponse response) {

        //????????????????????????
        try {
            response.setContentType("image/jpeg");
            byte[] decode = this.fileInfoService.previewAvatar();
            response.getOutputStream().write(decode);
        } catch (IOException e) {
            throw new ServiceException(CoreExceptionEnum.SERVICE_ERROR);
        }

        return null;
    }

    /**
     * ????????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:59 PM
     */
    @RequestMapping("/currentUserInfo")
    @ResponseBody
    public ResponseData getUserInfo() {

        ShiroUser currentUser = ShiroKit.getUser();
        if (currentUser == null) {
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }

        return new SuccessResponseData(userService.getUserInfo(currentUser.getId()));
    }

    /**
     * layui???????????? ????????????????????????
     *
     * @author gcj
     * @Date 2019-2-23 10:48:29
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData layuiUpload(@RequestPart("file") MultipartFile file) {

        String fileId = this.fileInfoService.uploadFile(file);

        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);

        return ResponseData.success(0, "????????????", map);
    }


}
