package com.qucai.kp.kd100.subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qucai.kp.context.RedisPrefix;
import com.qucai.kp.tool.HttpTool;
import com.qucai.kp.tool.JsonTool;
import com.qucai.kp.tool.RedisTool;

public class SubscribeUtil {
	
	private static Logger logger = LoggerFactory
			.getLogger(SubscribeUtil.class);

	public static void publishSubscribeAuto(SubscribeReq req) {
		List<String> companyList = new ArrayList<String>();
		
		if("other".equals(req.getCompany())) {
			companyList.addAll(autoCheckCarrier(req.getNumber()));
		} else {
			companyList.add(req.getCompany());
		}
		for(String company : companyList) {
			req.setCompany(company);
			publishSubscribe(req);
		}
	}
	
	public static void publishSubscribe(SubscribeReq req) {
		req.setKey(RedisTool.hget(RedisPrefix.REDIS_KEY_CONFIG_PREFIX.toString(), "KD100_KEY"));
		req.getParameters().put("callbackurl",
				RedisTool.hget(RedisPrefix.REDIS_KEY_CONFIG_PREFIX.toString(), "KD100_CALLBACK_URL"));
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schema", "json");
		paramMap.put("param", JsonTool.genByFastJson(req));
		String ret = HttpTool.callApiByPost(false,
				RedisTool.hget(RedisPrefix.REDIS_KEY_CONFIG_PREFIX.toString(), "KD100_SUBSCRIBE_URL"), paramMap);
		SubscribeResp ssr = JsonTool.resolveByFastJson(new TypeReference<SubscribeResp>() {
		}, ret);
		
		logger.info("向快递100推送订阅---单号:{},公司:{},推送结果:{}--{}",req.getNumber(), req.getCompany(), ssr.getResult(), ssr.getMessage());
	}
	
	public static List<String> autoCheckCarrier(String trackingNo) {
		List<String> companyList = new ArrayList<String>();
		String url = "http://www.kuaidi100.com/autonumber/auto?num=" + trackingNo + "&key=" + RedisTool.hget(RedisPrefix.REDIS_KEY_CONFIG_PREFIX.toString(), "KD100_KEY");
		String jsonArrayStr = HttpTool.callApiByGet(false, url);
		List<Map<String, Object>> carrierList = JSON.parseObject(jsonArrayStr, 
				new TypeReference<List<Map<String, Object>>>() {});
		for(Map<String, Object> m : carrierList) {
			companyList.add(String.valueOf(m.get("comCode")));
		}
		return companyList;
	}
}
