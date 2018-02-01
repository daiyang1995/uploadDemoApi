package com.qucai.kp.exception;

public class CommonException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 8188501947589552747L;
    private String ret;
    private String msg;

    public CommonException(String ret, String msg) {
        super();
        this.ret = ret;
        this.msg = msg;
    }

    /**
     * @return the ret
     */
    public String getRet() {
        return ret;
    }

    /**
     * @param ret
     *            the ret to set
     */
    public void setRet(String ret) {
        this.ret = ret;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
