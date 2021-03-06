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
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.tdt.base.log.BussinessLog;
import com.tdt.sys.core.constant.Const;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.exception.enums.BizExceptionEnum;
import com.tdt.sys.core.log.LogObjectHolder;
import com.tdt.sys.modular.system.entity.Dept;
import com.tdt.base.shiro.annotion.Permission;
import com.tdt.sys.core.constant.dictmap.DeptDict;
import com.tdt.base.pojo.node.TreeviewNode;
import com.tdt.base.pojo.node.ZTreeNode;
import com.tdt.base.pojo.page.LayuiPageFactory;
import com.tdt.sys.modular.system.model.DeptDto;
import com.tdt.sys.modular.system.service.DeptService;
import com.tdt.sys.modular.system.warpper.DeptTreeWrapper;
import com.tdt.sys.modular.system.warpper.DeptWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.treebuild.DefaultTreeBuildFactory;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tdt.sys.core.constant.dictmap.DeptDict;
import com.tdt.sys.core.constant.factory.ConstantFactory;
import com.tdt.sys.core.log.LogObjectHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * ???????????????
 *
 * @author gcj
 * @Date 2017???2???17???20:27:22
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private String PREFIX = "/modular/system/dept/";

    @Autowired
    private DeptService deptService;

    /**
     * ???????????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }
/**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:31 PM
     */
    @Permission
    @RequestMapping(value = "/dept_assign/{deptId}")
    public String deptAssign(@PathVariable("deptId") Long deptId, Model model) {
        if (ToolUtil.isEmpty(deptId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("deptId", deptId);
        return PREFIX + "/dept_assign.html";
    }
    /**
     * ?????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/dept_update")
    public String deptUpdate(@RequestParam("deptId") Long deptId) {

        if (ToolUtil.isEmpty(deptId)) {
            throw new RequestEmptyException();
        }

        //?????????????????????????????????
        Dept dept = deptService.getById(deptId);
        LogObjectHolder.me().set(dept);

        return PREFIX + "dept_edit.html";
    }

    /**
     * ???????????????tree?????????ztree??????
     *
     * @author gcj
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * ???????????????tree?????????treeview??????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/treeview")
    @ResponseBody
    public List<TreeviewNode> treeview() {
        List<TreeviewNode> treeviewNodes = this.deptService.treeviewNodes();

        //?????????
        DefaultTreeBuildFactory<TreeviewNode> factory = new DefaultTreeBuildFactory<>();
        factory.setRootParentId("0");
        List<TreeviewNode> results = factory.doTreeBuild(treeviewNodes);

        //???????????????????????????null
        DeptTreeWrapper.clearNull(results);

        return results;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "????????????", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(Dept dept) {
        this.deptService.addDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * ????????????????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(value = "condition", required = false) String condition,
                       @RequestParam(value = "deptId", required = false) Long deptId) {
        Page<Map<String, Object>> list = this.deptService.list(condition, deptId);
        Page<Map<String, Object>> wrap = new DeptWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Long deptId) {
        Dept dept = deptService.getById(deptId);
        DeptDto deptDto = new DeptDto();
        BeanUtil.copyProperties(dept, deptDto);
        deptDto.setPName(ConstantFactory.me().getDeptName(deptDto.getPid()));
        return deptDto;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "????????????", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(Dept dept) {
        deptService.editDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "????????????", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long deptId) {

        //??????????????????????????????
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }
/**
     * ????????????
     *
     * @author gcj
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping("/setAuthority")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData setAuthority(@RequestParam("deptId") Long deptId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(deptId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.deptService.setAuthority(deptId, ids);
        return SUCCESS_TIP;
    }
}
