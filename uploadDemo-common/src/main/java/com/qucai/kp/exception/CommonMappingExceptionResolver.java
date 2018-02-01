package com.qucai.kp.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理web请求、经由controller引发的异常<br>
 * <b>支持ajax的异常处理</b>
 * <p>ajax异常在error方法中捕获，方式如下：<br>
 * .error(function(data) {<br>
 *  var result = eval("("+data.responseText+")");<br>
 *  alert(result.msg);<br>
 * 	})<br>
 * @author owner
 *
 */
public class CommonMappingExceptionResolver extends
		SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		// 输出全部异常堆栈信息
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        logger.error(sw.toString());
        
		if (request.getHeader("X-Requested-With") == null) {
			return super.doResolveException(request, response, handler, ex);
		}else{
			// ajax请求
			try {
				JSONObject jsonObject = new JSONObject();
                jsonObject.put("ret", "500");
                jsonObject.put("msg", ex.getMessage());

	            response.setHeader("Content-Type", "text/html;charset=UTF-8");
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            PrintWriter out = response.getWriter();
	            out.print(jsonObject.toString());
	            out.flush();
	            out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 如果返回的不是null，表示已经处理结束
	        // 否则表示交给下一个异常处理器处理
	        // 返回无参的ModelAndView，不会跳转页面，使用write向客户端返回信息
			return new ModelAndView();
		}
	}

}
