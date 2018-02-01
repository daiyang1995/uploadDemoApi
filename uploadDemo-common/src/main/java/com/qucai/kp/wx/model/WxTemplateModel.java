package com.qucai.kp.wx.model;

public class WxTemplateModel {
    //用户OpenID
    private String touser;
    //模板消息ID
    private String template_id;
    //URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
    private String url;
    //标题颜色
    private String topcolor;
    //详细内容
    private WxTemplateData data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String templateId) {
        template_id = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public WxTemplateData getData() {
        return data;
    }

    public void setData(WxTemplateData data) {
        this.data = data;
    }

}
