package com.qucai.kp.kd100.callback;

import java.util.Date;

/**
 * 快递100跟踪信息
 * 
 * @version 1.0 2016-10-18
 */
public class Kd100record {
    /**
     * id
     */
    private String id;

    /**
     * 业务记录id
     */
    private String bid;

    /**
     * 业务类型
     */
    private String type;

    /**
     * 流水号
     */
    private String context;

    /**
     * 原始格式时间
     */
    private Date time;

    /**
     * 格式化后时间
     */
    private Date ftime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * id
     * @return 
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 业务记录id
     * @return 
     */
    public String getBid() {
        return bid;
    }

    /**
     * 业务记录id
     * @param bid
     */
    public void setBid(String bid) {
        this.bid = bid == null ? null : bid.trim();
    }

    /**
     * 业务类型
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * 业务类型
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 流水号
     * @return 
     */
    public String getContext() {
        return context;
    }

    /**
     * 流水号
     * @param context
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * 原始格式时间
     * @return 
     */
    public Date getTime() {
        return time;
    }

    /**
     * 原始格式时间
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 格式化后时间
     * @return 
     */
    public Date getFtime() {
        return ftime;
    }

    /**
     * 格式化后时间
     * @param ftime
     */
    public void setFtime(Date ftime) {
        this.ftime = ftime;
    }

    /**
     * 备注
     * @return 
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建人
     * @return 
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改人
     * @return 
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 修改人
     * @param modifier
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 修改时间
     * @return 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 修改时间
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}