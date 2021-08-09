package com.tdt.modular.instorage.mapper;

import com.tdt.modular.instorage.entity.PurchaseDetail;
import com.tdt.modular.instorage.model.params.PurchaseDetailParam;
import com.tdt.modular.instorage.model.result.PurchaseDetailResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gcj
 * @since 2019-08-21
 */
public interface PurchaseDetailMapper extends BaseMapper<PurchaseDetail> {

    /**
     * 获取列表
     *
     * @author gcj
     * @Date 2019-08-21
     */
    List<PurchaseDetailResult> customList(@Param("paramCondition") PurchaseDetailParam paramCondition);

    /**
     * 获取map列表
     *
     * @author gcj
     * @Date 2019-08-21
     */
    List<Map<String, Object>> customMapList(@Param("page") Page page,@Param("paramCondition") PurchaseDetailParam paramCondition);

    /**
     * 根据采购订单id获取采购订单详情map列表
     * @param page
     * @param pid
     * @return
     */
    List<Map<String, Object>> selectByPid(@Param("page") Page page,@Param("pid") Long pid);
    /**
     * 获取采购订单详情列表
     *
     * @author gcj
     * @Date 2019-08-21
     */
    List<Map<String, Object>> list(@Param("page") Page page,@Param("paramCondition") PurchaseDetailParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author gcj
     * @Date 2019-08-21
     */
    Page<PurchaseDetailResult> customPageList(@Param("page") Page page, @Param("paramCondition") PurchaseDetailParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author gcj
     * @Date 2019-08-21
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") PurchaseDetailParam paramCondition);

}
