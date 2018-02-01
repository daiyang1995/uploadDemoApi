package com.qucai.kp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.qucai.kp.pager.PageParam;

public class Tool {

    public static PageParam genPageParam(HttpServletRequest request) {
        String pageNum = request.getParameter("pageNum") == null ? "1"
                : request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize") == null ? "10"
                : request.getParameter("pageSize");
        PageParam pp = new PageParam();
        pp.setPageNum(Integer.parseInt(pageNum));
        pp.setPageSize(Integer.parseInt(pageSize));
        return pp;
    }
    
    public static PageParam genPageParamByJson(JSONObject json) {
        String pageNumStr = json.getString("pageNum") == null ? "1" : json.getString("pageNum");
        String pageSizeStr = json.getString("pageSize") == null ? "10" : json.getString("pageSize");
        Integer pageNum = 1;
        Integer pageSize = 10;
        try{
            pageNum = Integer.parseInt(pageNumStr);
            pageSize = Integer.parseInt(pageSizeStr);
        }catch(Exception e){
        }
        PageParam pp = new PageParam();
        pp.setPageNum(pageNum);
        pp.setPageSize(pageSize);
        return pp;
    }

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getFileAbsolutePath(HttpServletRequest request,
			String path) {
		return request.getSession().getServletContext().getRealPath(path);
	}

	public static String hideIdCard(String idCard) {
		return idCard.substring(0, 2)
				+ StringUtils.leftPad(idCard.substring(idCard.length() - 4),
						idCard.length() - 2, "*");
	}
	
	public static String genInviteCode(int userId) {
		return StringUtils.leftPad(Integer.toHexString(userId), 6, "0");
	}

	public static long datetimeNullConvert(Date d, int def) {
		return d == null ? def : d.getTime();
	}

	public static int numberNullConvert(Integer o, int def) {
		return o == null ? def : o;
	}

	public static Double numberNullConvert(Double o, double def) {
		return o == null ? def : o;
	}

	public static BigDecimal numberNullConvert(BigDecimal o, BigDecimal def) {
		return o == null ? def : o;
	}

	public static String stringNullConvert(String s) {
		return s == null ? "" : s;
	}
	
	
	public static String getStringRandom(int length) {  

        String val = "";  
        //length为几位密码 
        for(int i = 0; i < length; i++) {
        	String s = Integer.toString((int)(Math.random()*3)); 
        	int ran = 0 ;
        	/**
        	 * 0:数字 48-57
        	 * 1:字母 90-65 122 -97
        	 * 2:符号 126-123   96 -91  64-57 48-33 
        	 */
        	switch (s) {
			case "0": // 数字
				ran = (int)(Math.random()*10); // 0-9
				val += (char)(ran + 48);
				break;
			case "1":
				ran =(int)(Math.random()*26);//0~25 
				int temp= (int)(Math.random()*2) % 2 == 0 ? 65 : 97; //0~1  0为65大写  1为97小写
				val += (char)( ran + temp);    
				break;
			case "2":
				String s1 = Integer.toString((int)(Math.random()*4));  //区分特殊符号区间
				switch (s1) {
				case "0":// 126-123  
					ran = (int)(Math.random()*4);  //0~3
					val += (char)((int)(ran + 123));
					break;
				case "1":// 96 -91  
					ran = (int)(Math.random()*6);  //0~5
					val += (char)((int)(ran + 91));
					break;
				case "2"://64-57
					ran = (int)(Math.random()*8);  //0~7
					val += (char)((int)(ran + 57));
					break;
				case "3":// 48-33 
					ran = (int)(Math.random()*16);  //0~15
					val += (char)((int)(ran + 33));
					break;
				default:
					//33 ~126
					ran = (int)(Math.random()*94); //0~93
					val += (char)((int)(ran + 33));
					break;
				}
				break;
			default:
				//33 ~126
				ran = (int)(Math.random()*94); //0~93
				val += (char)((int)(ran + 33));
				break;
			}
        }  
        return val;  
    }  
	/**
	 * 获取保险公司 id_type
	 * 19为出错的
	 * @param identifyType
	 * @return
	 */
	public static String getInsurerCardType(String identifyType){
		String idType = "19";
		switch (identifyType) {
		case "身份证":
			idType = "01";
			break;
		case "户口薄":
			idType = "02";
			break;
		case "护照":
			idType = "03";
			break;
		case "军官证":
			idType = "04";
			break;
		case "驾驶执照":
			idType = "05";
			break;
		case "返乡证":
			idType = "06";
			break;
		case "组织机构代码证":
			idType = "07";
			break;
		case "士兵证":
			idType = "08";
			break;
		case "临时身份证":
			idType = "09";
			break;
		case "警官证":
			idType = "10";
			break;
		case "学生证":
			idType = "11";
			break;
		case "军官离退休证":
			idType = "12";
			break;
		case "港澳通行证":
			idType = "13";
			break;
		case "台湾通行证":
			idType = "14";
			break;
		case "旅行证":
			idType = "15";
			break;
		case "居留证":
			idType = "16";
			break;
		case "中国护照":
			idType = "17";
			break;
		case "台胞证":
			idType = "18";
			break;
		case "无法收集证件资料":
			idType = "19";
			break;
		case "异常身份证":
			idType = "20";
			break;
		case "税务登记证":
			idType = "21";
			break;
		case "营业执照":
			idType = "22";
			break;
		case "中华人民共和国组织机构代码证":
			idType = "23";
			break;
		case "社保保险登记证":
			idType = "24";
			break;
		case "办学许可证":
			idType = "25";
			break;
		case "其他":
			idType = "99";
			break;
		default:
			idType = "19";
			break;
		}
		return idType;
	}
	
	public static String getInsureTypeStr(int insureType){
		String s="";
		switch (Integer.toString(insureType)) {
		case "0":
			s = "日付";
			break;
		case "1":
			s = "月付";
			break;
		case "2":
			s = "年付";
			break;
		default:
			break;
		}
		return s;
	}
	public static String toChinese(String string) {
	        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

	        String result = "";

	        int n = string.length();
	        for (int i = 0; i < n; i++) {

	            int num = string.charAt(i) - '0';

	            if (i != n - 1 && num != 0) {
	                result += s1[num] + s2[n - 2 - i];
	            } else {
	                result += s1[num];
	            }
	            System.out.println("  "+result);
	        }
	        return result;
	    }
	/**
	 * 根据保险公司 id_type 获取idTypeStr 中文
	 * 无法收集证件资料 为出错的
	 * @param identifyType
	 * @return
	 */
	public static String getInsurerCardTypeStr(String identifyType){
		String idType = "无法收集证件资料";
		switch (identifyType) {
		case "01":
			idType = "身份证";
			break;
		case "02":
			idType = "户口薄";
			break;
		case "03":
			idType = "护照";
			break;
		case "04":
			idType = "军官证";
			break;
		case "05":
			idType = "驾驶执照";
			break;
		case "06":
			idType = "返乡证";
			break;
		case "07":
			idType = "组织机构代码证";
			break;
		case "08":
			idType = "士兵证";
			break;
		case "09":
			idType = "临时身份证";
			break;
		case "10":
			idType = "警官证";
			break;
		case "11":
			idType = "学生证";
			break;
		case "12":
			idType = "军官离退休证";
			break;
		case "13":
			idType = "港澳通行证";
			break;
		case "14":
			idType = "台湾通行证";
			break;
		case "15":
			idType = "旅行证";
			break;
		case "16":
			idType = "居留证";
			break;
		case "17":
			idType = "中国护照";
			break;
		case "18":
			idType = "台胞证";
			break;
		case "19":
			idType = "无法收集证件资料";
			break;
		case "20":
			idType = "异常身份证";
			break;
		case "21":
			idType = "税务登记证";
			break;
		case "22":
			idType = "营业执照";
			break;
		case "23":
			idType = "中华人民共和国组织机构代码证";
			break;
		case "24":
			idType = "社保保险登记证";
			break;
		case "25":
			idType = "办学许可证";
			break;
		case "99":
			idType = "其他";
			break;
		default:
			idType = "无法收集证件资料";
			break;
		}
		return idType;
	}
	public static String xmlChangeKeyWord(String word){
		word = word.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("&", "&amp;")
				.replaceAll("'", "&apos;")
				.replaceAll("\"", "&quot;");
		
		return word;
	}
	public static Boolean checkSpecialCharacters(String str){
		// ~ ! @ # $ % ^ & * + | } { " : < > ? / ; ' ' [ ] \ = ` 。
		String reg =  "[~!@#$%^&*+=|}{\":<>?/;'\\[\\]\\\\=`。]";  
		Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
		return m.find();
	}

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
