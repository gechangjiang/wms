package com.tdt.modular.outstore.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gcj
 * @since 2019-09-18
 */
@Data
public class ReviewParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 出库订单id
     */
    private Long outorderid;

    /**
     * 出库订单编号
     */
    private String outorderno;

    /**
     * 状态
     */
    private String state;

    /**
     * 仓库id
     */
    private Long warehouseid;

    /**
     * 创建人id
     */
    private Long createid;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createtime;

    @Override
    public String checkParam() {
        return null;
    }

}
