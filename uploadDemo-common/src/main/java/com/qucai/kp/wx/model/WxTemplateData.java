package com.qucai.kp.wx.model;

public class WxTemplateData {
    private WxTemplateDataNote first;
    //日期
    private WxTemplateDataNote keyword1;
    //订单编号
    private WxTemplateDataNote keyword2;
    //订单类型
    private WxTemplateDataNote keyword3;
    //联系人
    private WxTemplateDataNote keyword4;
    //联系电话
    private WxTemplateDataNote keyword5;

    private WxTemplateDataNote remark;

    public WxTemplateDataNote getFirst() {
        return first;
    }

    public void setFirst(WxTemplateDataNote first) {
        this.first = first;
    }

    public WxTemplateDataNote getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(WxTemplateDataNote keyword1) {
        this.keyword1 = keyword1;
    }

    public WxTemplateDataNote getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(WxTemplateDataNote keyword2) {
        this.keyword2 = keyword2;
    }

    public WxTemplateDataNote getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(WxTemplateDataNote keyword3) {
        this.keyword3 = keyword3;
    }

    public WxTemplateDataNote getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(WxTemplateDataNote keyword4) {
        this.keyword4 = keyword4;
    }

    public WxTemplateDataNote getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(WxTemplateDataNote keyword5) {
        this.keyword5 = keyword5;
    }

    public WxTemplateDataNote getRemark() {
        return remark;
    }

    public void setRemark(WxTemplateDataNote remark) {
        this.remark = remark;
    }

}
