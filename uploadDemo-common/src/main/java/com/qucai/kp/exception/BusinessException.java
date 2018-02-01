package com.qucai.kp.exception;

import com.qucai.kp.exception.CommonException;

public class BusinessException extends CommonException {

    /**
     * 
     */
    private static final long serialVersionUID = -5471670665487381036L;

    public BusinessException(String ret, String msg) {
        super(ret, msg);
    }

}
