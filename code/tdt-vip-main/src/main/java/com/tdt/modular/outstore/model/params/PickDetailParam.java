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
 * @since 2019-09-17
 */
@Data
public class PickDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 拣货id
     */
    private Long pid;

    /**
     * 库存日志id
     */
    private Long stocklogid;

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
     * 货位id
     */
    private Long locatorid;

    /**
     * 货位编码
     */
    private String locatorcode;

    /**
     * 货位名称
     */
    private String locatorname;

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

    @Override
    public String checkParam() {
        return null;
    }

}
