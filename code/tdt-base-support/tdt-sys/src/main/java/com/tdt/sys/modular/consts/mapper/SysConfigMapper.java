package com.tdt.sys.modular.consts.mapper;

import com.tdt.sys.modular.consts.entity.SysConfig;
import com.tdt.sys.modular.consts.model.params.SysConfigParam;
import com.tdt.sys.modular.consts.model.result.SysConfigResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tdt.sys.modular.consts.entity.SysConfig;
import com.tdt.sys.modular.consts.model.params.SysConfigParam;
import com.tdt.sys.modular.consts.model.result.SysConfigResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参数配置 Mapper 接口
 * </p>
 *
 * @author gcj
 * @since 2019-06-20
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 获取列表
     *
     * @author gcj
     * @Date 2019-06-20
     */
    List<SysConfigResult> customList(@Param("paramCondition") SysConfigParam paramCondition);

    /**
     * 获取map列表
     *
     * @author gcj
     * @Date 2019-06-20
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysConfigParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author gcj
     * @Date 2019-06-20
     */
    Page<SysConfigResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysConfigParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author gcj
     * @Date 2019-06-20
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysConfigParam paramCondition);

}
