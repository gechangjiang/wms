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
import com.tdt.sys.core.constant.dictmap.NoticeMap;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.exception.enums.BizExceptionEnum;
import com.tdt.sys.modular.system.entity.Notice;
import com.tdt.base.log.BussinessLog;
import com.tdt.sys.core.constant.dictmap.DeleteDict;
import com.tdt.base.pojo.page.LayuiPageFactory;
import com.tdt.sys.core.log.LogObjectHolder;
import com.tdt.sys.core.shiro.ShiroKit;
import com.tdt.sys.modular.system.service.NoticeService;
import com.tdt.sys.modular.system.warpper.NoticeWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tdt.sys.core.constant.dictmap.DeleteDict;
import com.tdt.sys.core.constant.dictmap.NoticeMap;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.exception.enums.BizExceptionEnum;
import com.tdt.sys.core.log.LogObjectHolder;
import com.tdt.sys.core.shiro.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * ???????????????
 *
 * @author gcj
 * @Date 2017-05-09 23:02:21
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private String PREFIX = "/modular/system/notice/";

    @Autowired
    private NoticeService noticeService;

    /**
     * ???????????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Long noticeId, Model model) {
        Notice notice = this.noticeService.getById(noticeId);
        model.addAllAttributes(BeanUtil.beanToMap(notice));
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * ??????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = this.noticeService.list(condition);
        Page<Map<String, Object>> wrap = new NoticeWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "????????????", key = "title", dict = NoticeMap.class)
    public Object add(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreateUser(ShiroKit.getUserNotNull().getId());
        notice.setCreateTime(new Date());
        this.noticeService.save(notice);
        return SUCCESS_TIP;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "????????????", key = "noticeId", dict = DeleteDict.class)
    public Object delete(@RequestParam Long noticeId) {

        //??????????????????
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

        this.noticeService.removeById(noticeId);

        return SUCCESS_TIP;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:06 PM
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "????????????", key = "title", dict = NoticeMap.class)
    public Object update(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getNoticeId(), notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Notice old = this.noticeService.getById(notice.getNoticeId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        this.noticeService.updateById(old);
        return SUCCESS_TIP;
    }

}
