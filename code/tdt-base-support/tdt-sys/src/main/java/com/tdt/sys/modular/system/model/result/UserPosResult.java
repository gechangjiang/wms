package com.tdt.sys.modular.system.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户职位关联表
 * </p>
 *
 * @author www.qiqucode.com
 * @since 2019-06-28
 */
@Data
public class UserPosResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long userPosId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 职位id
     */
    private Long posId;

}
