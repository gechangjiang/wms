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
 * @author chenc
 * @since 2019-09-18
 */
@Data
public class ReviewDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 出库复核主表id
     */
    private Long pid;

    /**
     * 商品id
     */
    private Long commodityid;

    /**
     * 商品编码
     */
    private String commoditybar;

    /**
     * 商品名
     */
    private String commodityname;

    /**
     * 数量
     */
    private Integer qty;

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

    /**
     * 修改人id
     */
    private Long updateid;

    /**
     * 修改人
     */
    private String updator;

    /**
     * 修改时间
     */
    private Date updatetime;

    @Override
    public String checkParam() {
        return null;
    }

}
