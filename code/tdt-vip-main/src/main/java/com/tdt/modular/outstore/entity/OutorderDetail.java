package com.tdt.modular.outstore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gcj
 * @since 2019-09-10
 */
@TableName("o_outorder_detail")
public class OutorderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 出库表id
     */
    @TableField("pid")
    private Long pid;

    /**
     * 库存日志表id
     */
    @TableField("stocklogid")
    private Long stocklogid;

    /**
     * 商品id
     */
    @TableField("commodityid")
    private Long commodityid;

    /**
     * 商品编码
     */
    @TableField("commoditybar")
    private String commoditybar;

    /**
     * 商品名
     */
    @TableField("commodityname")
    private String commodityname;

    /**
     * 数量
     */
    @TableField("qty")
    private Integer qty;

    /**
     * 创建人id
     */
    @TableField("createid")
    private Long createid;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("createtime")
    private Date createtime;

    /**
     * 修改人id
     */
    @TableField("updateid")
    private Long updateid;

    /**
     * 修改人
     */
    @TableField("updator")
    private String updator;

    /**
     * 修改时间
     */
    @TableField("updatetime")
    private Date updatetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getStocklogid() {
        return stocklogid;
    }

    public void setStocklogid(Long stocklogid) {
        this.stocklogid = stocklogid;
    }

    public Long getCommodityid() {
        return commodityid;
    }

    public void setCommodityid(Long commodityid) {
        this.commodityid = commodityid;
    }

    public String getCommoditybar() {
        return commoditybar;
    }

    public void setCommoditybar(String commoditybar) {
        this.commoditybar = commoditybar;
    }

    public String getCommodityname() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getUpdateid() {
        return updateid;
    }

    public void setUpdateid(Long updateid) {
        this.updateid = updateid;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "OutorderDetail{" +
        "id=" + id +
        ", pid=" + pid +
        ", stocklogid=" + stocklogid +
        ", commodityid=" + commodityid +
        ", commoditybar=" + commoditybar +
        ", commodityname=" + commodityname +
        ", qty=" + qty +
        ", createid=" + createid +
        ", creator=" + creator +
        ", createtime=" + createtime +
        ", updateid=" + updateid +
        ", updator=" + updator +
        ", updatetime=" + updatetime +
        "}";
    }
}
