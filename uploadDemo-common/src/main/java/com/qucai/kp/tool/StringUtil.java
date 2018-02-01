package com.qucai.kp.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class StringUtil {

    public static final String chineseChar = "\\u3400-\\u4DB5\\u4E00-\\u9FA5\\u9FA6-\\u9FBB\\uF900-\\uFA2D\\uFA30-\\uFA6A\\uFA70-\\uFAD9\\uFF00-\\uFFEF\\u2E80-\\u2EFF\\u3000-\\u303F\\u31C0-\\u31EF\\u2F00-\\u2FDF\\u2FF0-\\u2FFF\\u3100-\\u312F\\u31A0-\\u31BF\\u3040-\\u309F\\u30A0-\\u30FF\\u31F0-\\u31FF\\uAC00-\\uD7AF\\u1100-\\u11FF\\u3130-\\u318F\\u4DC0-\\u4DFF\\uA000-\\uA48F\\uA490-\\uA4CF\\u2800-\\u28FF\\u3200-\\u32FF\\u3300-\\u33FF\\u2700-\\u27BF\\u2600-\\u26FF\\uFE10-\\uFE1F\\uFE30-\\uFE4F";
    public static final String num09 = "\\u0030-\\u0039";
    public static final String caz = "\\u0061-\\u007a";
    public static final String cAZ = "\\u0041-\\u005a";
    public static final String ascii = "\\u0020-\\u007e";

    public static final String reg = "[^" + chineseChar + ascii + "]";

    public static String filterEmoji(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.replaceAll(reg, " ");
        } else {
            return str;
        }
    }

    public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
