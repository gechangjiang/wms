package com.tdt.sys.modular.system.service;

import com.tdt.base.pojo.page.LayuiPageInfo;
import com.tdt.sys.modular.system.entity.UserPos;
import com.tdt.sys.modular.system.model.params.UserPosParam;
import com.tdt.sys.modular.system.model.result.UserPosResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户职位关联表 服务类
 * </p>
 *
 * @author gcj
 * @since 2019-06-28
 */
public interface UserPosService extends IService<UserPos> {

    /**
     * 新增
     *
     * @author gcj
     * @Date 2019-06-28
     */
    void add(UserPosParam param);

    /**
     * 删除
     *
     * @author gcj
     * @Date 2019-06-28
     */
    void delete(UserPosParam param);

    /**
     * 更新
     *
     * @author gcj
     * @Date 2019-06-28
     */
    void update(UserPosParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author gcj
     * @Date 2019-06-28
     */
    UserPosResult findBySpec(UserPosParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author gcj
     * @Date 2019-06-28
     */
    List<UserPosResult> findListBySpec(UserPosParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author gcj
     * @Date 2019-06-28
     */
     LayuiPageInfo findPageBySpec(UserPosParam param);

}
