package com.qucai.kp.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@SuppressWarnings("rawtypes")
public class AliApiTool {

    private static String bankUrl;
    private static String bankAppCode;
    private static String bankAddressUrl;
    private static String bankAddressAppCode;

    private static String idCardUrl;
    private static String idCardAppCode;

    private static String passportUrl;
    private static String passportAppCode;

    static {
        InputStream in = AliApiTool.class.getResourceAsStream("/ali4api.properties");
        try {
            Properties properties = new Properties();
            properties.load(in);

            bankUrl = properties.getProperty("aliapi.bankUrl");
            bankAppCode = properties.getProperty("aliapi.bankAppCode");
            bankAddressUrl = properties.getProperty("aliapi.bankAddressUrl");
            bankAddressAppCode = properties.getProperty("aliapi.bankAddressAppCode");
            idCardUrl = properties.getProperty("aliapi.idCardUrl");
            idCardAppCode = properties.getProperty("aliapi.idCardAppCode");
            passportUrl = properties.getProperty("aliapi.passportUrl");
            passportAppCode = properties.getProperty("aliapi.passportAppCode");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 银行卡识别
     * @author yhy 2017年12月12日
     * @param base64Str（不包含：data:image/jpg;base64,）
     * @return Map（成功：ret=0；失败：ret=-1）
     */
    public static Map<String, Object> bankImageDiscern(String base64Str) {
        if (StringUtils.isBlank(bankUrl) || StringUtils.isBlank(bankAppCode) || StringUtils.isBlank(base64Str)) {
            return null;
        }
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Authorization", "APPCODE " + bankAppCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        JSONObject paramJson = new JSONObject();
        JSONArray inputArray = new JSONArray();
        JSONObject imageJson = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("dataType", 50);
        json.put("dataValue", base64Str);
        imageJson.put("image", json);
        inputArray.add(imageJson);
        paramJson.put("inputs", inputArray);
        String bodys = paramJson.toString();
        try {
            Map<String, Object> retMap = JSON.parseObject(HttpTool.callApiByPostBody(true, bankUrl, headers, bodys),
                    new TypeReference<Map<String, Object>>() {
                    });
            List<Map> outputs = JSONArray.parseArray(String.valueOf(retMap.get("outputs")), Map.class);
            Map<String, Object> outputValue = JSON.parseObject(outputs.get(0).get("outputValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> dataValue = JSON.parseObject(outputValue.get("dataValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> rsMap = new HashMap<String, Object>();
            if ("true".equals(dataValue.get("success").toString().toLowerCase())) {
                rsMap.put("ret", "0");
                rsMap.put("bankCard", dataValue.get("card_num") == null ? null : dataValue.get("card_num"));
                if (StringUtils.isNotBlank(bankAddressUrl) && StringUtils.isNotBlank(bankAddressAppCode)
                        && rsMap.get("bankCard") != null) {
                    headers = new HashMap<String, Object>();
                    headers.put("Authorization", "APPCODE " + bankAddressAppCode);
                    //根据API的要求，定义相对应的Content-Type
                    headers.put("Content-Type", "application/json; charset=UTF-8");

                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("bankcard", rsMap.get("bankCard"));

                    try {
                        retMap = HttpTool.callApiReturnMap(true, bankAddressUrl, "get", headers, paramMap);
                        outputValue = JSON.parseObject(String.valueOf(retMap.get("result")),
                                new TypeReference<Map<String, Object>>() {
                                });
                        if ("0".equals(retMap.get("status").toString().toLowerCase()) && outputValue != null) {
                            rsMap.put("bankName", outputValue.get("bank") == null ? null : outputValue.get("bank"));
                            rsMap.put("province", outputValue.get("province") == null ? null : outputValue
                                    .get("province"));
                            rsMap.put("city", outputValue.get("city") == null ? null : outputValue.get("city"));
                            rsMap.put("type", outputValue.get("type") == null ? null : outputValue.get("type"));
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                rsMap.put("ret", "-1");
            }
            return rsMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("ret", "-1");
        return rsMap;
    }

    /**
     * 银行卡归属地查询
     * @author yhy 2017年12月12日
     * @param bankCard
     * @return Map（成功：ret=0；失败：ret=-1）
     */
    public static Map<String, Object> bankAddress4Card(String bankCard) {
        if (StringUtils.isBlank(bankAddressUrl) || StringUtils.isBlank(bankAddressAppCode)
                || StringUtils.isBlank(bankCard)) {
            return null;
        }
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Authorization", "APPCODE " + bankAddressAppCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("bankcard", bankCard);

        try {
            Map<String, Object> retMap = HttpTool.callApiReturnMap(true, bankAddressUrl, "get", headers, paramMap);
            System.out.println(retMap.toString());

            Map<String, Object> outputValue = JSON.parseObject(String.valueOf(retMap.get("result")),
                    new TypeReference<Map<String, Object>>() {
                    });
            if ("0".equals(retMap.get("status").toString().toLowerCase()) && outputValue != null) {
                Map<String, Object> rsMap = new HashMap<String, Object>();
                rsMap.put("ret", "0");
                rsMap.put("bankName", String.valueOf(outputValue.get("bank")));
                rsMap.put("province", String.valueOf(outputValue.get("province")));
                rsMap.put("city", String.valueOf(outputValue.get("city")));
                rsMap.put("type", String.valueOf(outputValue.get("type")));
                return rsMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("ret", "-1");
        return rsMap;
    }

    /**
     * 身份证影像识别
     * @author yhy 2018年1月3日
     * @param base64Str 影像base64（不包含：data:image/jpg;base64,）
     * @param side 影像方向（正面：face；反面：back）
     * @return
     */
    public static Map<String, Object> idCardImageDiscern(String base64Str, String side) {
        if (StringUtils.isBlank(idCardUrl) || StringUtils.isBlank(idCardAppCode) || StringUtils.isBlank(base64Str)
                || StringUtils.isBlank(side)) {
            return null;
        }
        if (!"face".equals(side.toLowerCase()) && !"back".equals(side.toLowerCase())) {
            return null;
        }
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Authorization", "APPCODE " + idCardAppCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        JSONObject paramJson = new JSONObject();
        JSONArray inputArray = new JSONArray();
        JSONObject imageJson = new JSONObject();
        JSONObject jsonValue = new JSONObject();
        jsonValue.put("dataType", 50);
        jsonValue.put("dataValue", base64Str);
        imageJson.put("image", jsonValue);
        JSONObject jsonConfigure = new JSONObject();
        jsonConfigure.put("dataType", 50);
        JSONObject sideJson = new JSONObject();
        sideJson.put("side", side.toLowerCase());
        jsonConfigure.put("dataValue", sideJson);
        imageJson.put("configure", jsonConfigure);
        inputArray.add(imageJson);
        paramJson.put("inputs", inputArray);
        String bodys = paramJson.toString();
        try {
            Map<String, Object> retMap = JSON.parseObject(HttpTool.callApiByPostBody(true, idCardUrl, headers, bodys),
                    new TypeReference<Map<String, Object>>() {
                    });
            List<Map> outputs = JSONArray.parseArray(String.valueOf(retMap.get("outputs")), Map.class);
            Map<String, Object> outputValue = JSON.parseObject(outputs.get(0).get("outputValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> dataValue = JSON.parseObject(outputValue.get("dataValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> rsMap = new HashMap<String, Object>();
            if ("true".equals(dataValue.get("success").toString().toLowerCase())) {
                rsMap.put("ret", "0");
                if ("face".equals(side.toLowerCase())) {
                    rsMap.put("address", String.valueOf(dataValue.get("address")));
                    rsMap.put("name", String.valueOf(dataValue.get("name")));
                    rsMap.put("idCard", String.valueOf(dataValue.get("num")));
                    rsMap.put("gender", String.valueOf(dataValue.get("sex")));
                    rsMap.put("date_of_birth", StringUtils.isBlank(String.valueOf(dataValue.get("birth"))) ? null
                            : String.valueOf(dataValue.get("birth")));
                    rsMap.put("nationality", String.valueOf(dataValue.get("nationality")));
                } else {
                    rsMap.put("start_date", dataValue.get("start_date") == null ? null : String.valueOf(dataValue
                            .get("start_date")));
                    rsMap.put("end_date", dataValue.get("end_date") == null ? null : String.valueOf(dataValue
                            .get("end_date")));
                    rsMap.put("issue", String.valueOf(dataValue.get("issue")));
                }
            } else {
                rsMap.put("ret", "-1");
            }
            return rsMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("ret", "-1");
        return rsMap;
    }

    /**
     * 护照影像识别
     * @author yhy 2018年1月3日
     * @param base64Str
     * @return
     */
    public static Map<String, Object> passportImageDiscern(String base64Str) {
        if (StringUtils.isBlank(passportUrl) || StringUtils.isBlank(passportAppCode) || StringUtils.isBlank(base64Str)) {
            return null;
        }
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Authorization", "APPCODE " + passportAppCode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");

        JSONObject paramJson = new JSONObject();
        JSONArray inputArray = new JSONArray();
        JSONObject imageJson = new JSONObject();
        JSONObject jsonValue = new JSONObject();
        jsonValue.put("dataType", 50);
        jsonValue.put("dataValue", base64Str);
        imageJson.put("image", jsonValue);
        inputArray.add(imageJson);
        paramJson.put("inputs", inputArray);
        String bodys = paramJson.toString();
        try {
            Map<String, Object> retMap = JSON.parseObject(
                    HttpTool.callApiByPostBody(true, passportUrl, headers, bodys),
                    new TypeReference<Map<String, Object>>() {
                    });
            List<Map> outputs = JSONArray.parseArray(String.valueOf(retMap.get("outputs")), Map.class);
            Map<String, Object> outputValue = JSON.parseObject(outputs.get(0).get("outputValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> dataValue = JSON.parseObject(outputValue.get("dataValue").toString(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Map<String, Object> rsMap = new HashMap<String, Object>();
            if ("true".equals(dataValue.get("success").toString().toLowerCase())) {
                rsMap.put("ret", "0");
                rsMap.put("date_of_birth", StringUtils.isBlank(String.valueOf(dataValue.get("birth_date"))) ? null
                        : String.valueOf(dataValue.get("birth_date")));
                rsMap.put("birth_address", String.valueOf(dataValue.get("birth_address")));
                rsMap.put("country", String.valueOf(dataValue.get("country")));
                rsMap.put("start_date", StringUtils.isBlank(String.valueOf(dataValue.get("expiry_date"))) ? null
                        : String.valueOf(dataValue.get("expiry_date")));
                rsMap.put("end_date", StringUtils.isBlank(String.valueOf(dataValue.get("issue_date"))) ? null : String
                        .valueOf(dataValue.get("issue_date")));
                rsMap.put("issue", String.valueOf(dataValue.get("issue_place")));
                rsMap.put("name_cn", String.valueOf(dataValue.get("name_cn")));
                rsMap.put("name_en", String.valueOf(dataValue.get("name")));
                rsMap.put("passport", String.valueOf(dataValue.get("passport_no")));
                if ("M".equals(String.valueOf(dataValue.get("sex")))) {
                    rsMap.put("gender", "男");
                } else if ("F".equals(String.valueOf(dataValue.get("sex")))) {
                    rsMap.put("gender", "女");
                }
                rsMap.put("type", String.valueOf(dataValue.get("type")));
            } else {
                rsMap.put("ret", "-1");
            }
            return rsMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("ret", "-1");
        return rsMap;
    }
}
