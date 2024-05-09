package org.example.hotchpotch.redis.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "content")
public class ApiResponseDto<T> {

    /** 成功 */
    public static String CODE_SUCCESS="0000";
    /** 失败 */
    public static String CODE_FAIL="1000";
    /** 系统异常 */
    public static String CODE_EXCEPTION="1001";
    /** 签名错误 */
    public static String CODE_ERR_SIGN="1002";
    /** 参数错误 */
    public static String CODE_ERR_PARAM="1003";
    /** 业务异常 */
    public static String CODE_BIZ_ERR="1004";
    /** 查询无数据，使用明确的参数(如id)进行查询时未找到记录时返回此错误码 */
    public static String CODE_NO_DATA="1005";
    /** 错误的请求方法 */
    public static String CODE_ERR_REQUEST_METHOD="1006";
    /** 错误的请求内容类型 */
    public static String CODE_ERR_CONTENT_TYPE="1007";
    /** 系统繁忙 */
    public static String CODE_SYS_BUSY="1008";
    /** 显示提示 */
    public static String CODE_SHOW_TIP="1009";
    /** 根据bizCode进行处理 */
    public static String CODE_DEAL_BIZ_CODE="1012";
    /** 未找到请求 */
    public static String CODE_NOT_FOUND_CODE="1013";

    public final static ApiResponseDto SUCCESS=new ApiResponseDto();


    private String code =CODE_SUCCESS;

    /** 状态说明 */
    private String msg ="success";

    /** 请求是否成功 */
    public boolean isSuccess(){
        return CODE_SUCCESS.equals(code);
    }

    /** 结果内容 */
    private T content;

    /** 时间戳 */
    private long timestamp=System.currentTimeMillis();

    /** 业务状态码，由业务接口定义 */
    private String bizCode;

    /** 业务状态说明 */
    private String bizMsg;

    public ApiResponseDto(T content) {
        this.content=content;
    }

    public static <T> ApiResponseDto<T> success(){
        return SUCCESS;
    }

    public static <T> ApiResponseDto<T> success(T content){
        return new ApiResponseDto<T>(content);
    }

    public static <T> ApiResponseDto<T> fail(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_FAIL);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> exception(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_EXCEPTION);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> errSign(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_ERR_SIGN);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> errParam(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_ERR_PARAM);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> bizErr(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_BIZ_ERR);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> notFound(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_NOT_FOUND_CODE);
        response.setMsg(msg);
        return response;
    }

    public static <T> ApiResponseDto<T> noData(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_NO_DATA);
        response.setMsg(msg);
        return response;
    }
    public static <T> ApiResponseDto<T>  errRequestMethod(String msg){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_ERR_REQUEST_METHOD);
        response.setMsg(msg);
        return response;
    }
    public static <T> ApiResponseDto<T> errContentType(){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_ERR_CONTENT_TYPE);
        response.setMsg("错误的请求内容类型");
        return response;
    }
    public static <T> ApiResponseDto<T> sysBusy(){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_SYS_BUSY);
        response.setMsg("系统繁忙");
        return response;
    }
    public static <T> ApiResponseDto<T>  showTip(String tip){
        ApiResponseDto<T> response = new ApiResponseDto<>();
        response.setCode(CODE_SHOW_TIP);
        response.setMsg(tip);
        return response;
    }

    public ApiResponseDto<T> bizInfo(String bizCode,String bizMsg){
        this.code=bizCode;
        this.msg=bizMsg;
        return this;
    }

    public static <T> ApiResponseDto<T>  dealBizCode(String bizCode,String bizMsg,T content){
        ApiResponseDto<T> response = new ApiResponseDto<>(content);
        response.setCode(CODE_DEAL_BIZ_CODE);
        response.setMsg("根据bizCode进行处理");
        response.setBizCode(bizCode);
        response.setBizMsg(bizMsg);
        return response;
    }
}
