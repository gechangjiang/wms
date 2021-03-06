package com.tdt.modular.base.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gcj
 * @since 2019-08-19
 */
@Data
public class WarehouseResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 仓库编号
     */
    private Long id;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库简称
     */
    private String alias;

    /**
     * 仓库地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

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

}
