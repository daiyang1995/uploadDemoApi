package com.qucai.kp.enums;


/**
 * 业务返回码定义规则如下：<br>
 * 模块名_方法名_返回码<br>
 * 每个方法至少保留一项默认返回码<br>
 * ret：如010101：01(模块)01(方法)01(异常)，其中，模块编号00代表业务系统的自定义系统级错误<br>
 * 
 * <p>说明:<br>
 * HTTP: Status 1xx  (临时响应)<br>
 * ->表示临时响应并需要请求者继续执行操作的状态代码。<br>
 * 
 * <p>详细代码及说明:<br>
 * HTTP: Status 100 (继续)<br>
 * -> 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。<br>
 * HTTP: Status 101 (切换协议)<br>
 * -> 请求者已要求服务器切换协议，服务器已确认并准备切换。<br>
 * 
 * <p>说明:<br>
 * HTTP Status 2xx  (成功)<br>
 * ->表示成功处理了请求的状态代码。<br>
 * 
 * <p>详细代码及说明:<br>
 * HTTP Status 200 (成功)<br>
 * -> 服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。<br>
 * HTTP Status 201 (已创建)<br>
 * -> 请求成功并且服务器创建了新的资源。<br>
 * HTTP Status 202 (已接受)<br>
 * -> 服务器已接受请求，但尚未处理。<br>
 * HTTP Status 203 (非授权信息)<br>
 * -> 服务器已成功处理了请求，但返回的信息可能来自另一来源。<br>
 * HTTP Status 204 (无内容)<br>
 * -> 服务器成功处理了请求，但没有返回任何内容。<br>
 * HTTP Status 205 (重置内容)<br>
 * -> 服务器成功处理了请求，但没有返回任何内容。<br>
 * HTTP Status 206 (部分内容)<br>
 * -> 服务器成功处理了部分 GET 请求。<br>
 * 
 * <p>说明:<br>
 * HTTP Status 4xx (请求错误)<br>
 * ->这些状态代码表示请求可能出错，妨碍了服务器的处理。<br>
 * 
 * <p>详细代码说明:<br>
 * HTTP Status 400 （错误请求） <br>
 * ->服务器不理解请求的语法。<br>
 * HTTP Status 401 （未授权） <br>
 * ->请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。<br>
 * HTTP Status 403 （禁止）<br>
 * -> 服务器拒绝请求。<br>
 * HTTP Status 404 （未找到） <br>
 * ->服务器找不到请求的网页。<br>
 * HTTP Status 405 （方法禁用） <br>
 * ->禁用请求中指定的方法。<br>
 * HTTP Status 406 （不接受） <br>
 * ->无法使用请求的内容特性响应请求的网页。<br>
 * HTTP Status 407 （需要代理授权） <br>
 * ->此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。<br>
 * HTTP Status 408 （请求超时） <br>
 * ->服务器等候请求时发生超时。<br>
 * HTTP Status 409 （冲突） <br>
 * ->服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息。<br>
 * HTTP Status 410 （已删除）<br>
 * -> 如果请求的资源已永久删除，服务器就会返回此响应。<br>
 * HTTP Status 411 （需要有效长度） <br>
 * ->服务器不接受不含有效内容长度标头字段的请求。<br>
 * HTTP Status 412 （未满足前提条件） <br>
 * ->服务器未满足请求者在请求中设置的其中一个前提条件。<br>
 * HTTP Status 413 （请求实体过大） <br>
 * ->服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。<br>
 * HTTP Status 414 （请求的 URI 过长） 
 * ->请求的 URI（通常为网址）过长，服务器无法处理。<br>
 * HTTP Status 415 （不支持的媒体类型） <br>
 * ->请求的格式不受请求页面的支持。<br>
 * HTTP Status 416 （请求范围不符合要求）<br>
 * ->如果页面无法提供请求的范围，则服务器会返回此状态代码。<br>
 * HTTP Status 417 （未满足期望值） <br>
 * ->服务器未满足”期望”请求标头字段的要求。<br>
 * 
 * <p>说明<br>
 * HTTP Status 5xx （服务器错误）<br>
 * ->这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。<br>
 * 
 * <p>代码详细及说明:<br>
 * HTTP Status 500 （服务器内部错误）<br>
 * ->服务器遇到错误，无法完成请求。<br>
 * HTTP Status 501 （尚未实施） <br>
 * ->服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码。<br>
 * HTTP Status 502 （错误网关） <br>
 * ->服务器作为网关或代理，从上游服务器收到无效响应。<br>
 * HTTP Status 503 （服务不可用）<br>
 * -> 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态。<br>
 * HTTP Status 504 （网关超时） <br>
 * ->服务器作为网关或代理，但是没有及时从上游服务器收到请求。<br>
 * HTTP Status 505 （HTTP 版本不受支持）<br>
 * -> 服务器不支持请求中所用的 HTTP 协议版本。<br>
 * 
 * @author owner
 *
 */
public enum RetEnum implements RetEnumIntf {

	// 基本
	SUCCESS("0", "成功"), 
	FAIL("-1", "失败"), 
	REQUEST_METHOD_ERROR("1", "请求方式错误"),
	SIGN_ERROR("2", "签名不合法"),
	
	HTTP_ERROR_400("400", "错误的请求"),
	HTTP_ERROR_401("401", "未授权"),
	HTTP_ERROR_403("403", "禁止访问"),
	HTTP_ERROR_403_6("403.6", "禁止访问：IP地址被拒绝"),
	HTTP_ERROR_500("500", "内部服务器错误"),
	
	@Deprecated
	SAMPLE_NONE_USED("_010101", "废弃不再使用的"), 
	@Deprecated
	SAMPLE_USED("_010102", "正在使用的");

	private String ret;
	private String msg;

	RetEnum(String ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
