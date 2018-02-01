package com.qucai.kp.tool;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.qucai.kp.enums.RetApiEnumIntf;
import com.qucai.kp.enums.RetEnumIntf;

public class JsonBizTool {

    public static String genJson(String ret, String msg) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ret", ret);
        map.put("msg", msg);
        return JsonTool.genByFastJson(map);
    }

    public static String genJson(RetEnumIntf rei) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ret", rei.getRet());
        map.put("msg", rei.getMsg());
        return JsonTool.genByFastJson(map);
    }

    public static String genJson(RetApiEnumIntf rei) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", rei.getCode());
        map.put("msg", rei.getMsg());
        return JsonTool.genByFastJson(map);
    }

    public static String genJson(RetEnumIntf rei, Map<String, Object> map) throws JSONException {
        map.put("ret", rei.getRet());
        map.put("msg", rei.getMsg());
        return JsonTool.genByFastJson(map);
    }

    public static String genJson(RetApiEnumIntf rei, Map<String, Object> map) throws JSONException {
        map.put("code", rei.getCode());
        map.put("msg", rei.getMsg());
        return JsonTool.genByFastJson(map);
    }

    public static String genJson(Map<String, Object> map) throws JSONException {
        return JsonTool.genByFastJson(map);
    }

}
