package com.qucai.kp.kd100.callback;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.TypeReference;
import com.qucai.kp.tool.JsonTool;

public class Kd100Callback extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = LoggerFactory
			.getLogger(Kd100Callback.class);
	
	// use this 'wac' get your service bean
	protected WebApplicationContext wac;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext sc = getServletContext();
		wac = WebApplicationContextUtils.getWebApplicationContext(sc);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CallbackResp resp = new CallbackResp();
		try {
			String param = request.getParameter("param");
			logger.info("快递100推送数据：" + param);
			
			CallbackReq cr = JsonTool.resolveByFastJson(
					new TypeReference<CallbackReq>() {
					}, param);
			
			// rewrite this method for your own business logic
			deal(cr, resp);

			// 这里必须返回，否则认为失败，过30分钟又会重复推送。
			response.getWriter().print(JsonTool.genByFastJson(resp));
		} catch (Exception e) {
			// 保存失败，服务端等30分钟会重复推送。
			resp.setResult(false);
			resp.setReturnCode("500");
			resp.setMessage("保存失败" + e.getMessage());
			response.getWriter().print(JsonTool.genByFastJson(resp));
		}
	}

	public void deal(CallbackReq cr, CallbackResp resp) throws Exception {
		// default response
		resp.setResult(true);
		resp.setReturnCode("200");
		resp.setMessage("成功");
	}
}
