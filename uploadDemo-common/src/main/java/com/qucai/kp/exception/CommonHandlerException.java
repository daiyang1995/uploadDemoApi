package com.qucai.kp.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.qucai.kp.enums.RetEnum;

public class CommonHandlerException implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory
            .getLogger(CommonHandlerException.class);

    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {

        // 输出全部异常堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        logger.error(sw.toString());

        try {
            JSONObject jsonObject = new JSONObject();

            if (ex instanceof CommonException) {
                jsonObject.put("ret", ((CommonException) ex).getRet());
                jsonObject.put("msg", ((CommonException) ex).getMsg());
            } else {
                jsonObject.put("ret", RetEnum.HTTP_ERROR_500.getRet());
                jsonObject.put("msg", RetEnum.HTTP_ERROR_500.getMsg());
            }

            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonObject.toString());
            out.flush();
            out.close();

        } catch (IOException e) {
            logger.error("CommonHandlerException 返回 RetEnum.HTTP_ERROR_500 发生错误");
            e.printStackTrace();
        }
        
        // 如果返回的不是null，表示已经处理结束
        // 否则表示交给下一个异常处理器处理
        // 返回无参的ModelAndView，不会跳转页面，使用write向客户端返回信息
        return new ModelAndView();
    }

}
