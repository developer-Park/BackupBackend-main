package ca.sait.backup.utils;

/**
 * Business logic json data form
 */
public class JsonData {

    /**
     * Status code: 0 means successful, 1 means processing, -1 means failure
     */
    private Integer code;

    /**
     * business data
     */
    private Object data;

    /**
     * information presentation
     */
    private String msg;

    public JsonData(){}

    public JsonData(Integer code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


    /**
     * 成功，不用返回数据
     * success, no return value.
     * @return
     */
    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }

    /**
     * 成功，返回数据
     * success, return value.
     * @param data
     * @return
     */
    public static JsonData buildSuccess(Object data){
        return new JsonData(0,data,null);
    }
    /**
     * 成功，返回消息
     * success, return message.
     * @param msg
     * @return
     */
    public static JsonData buildSuccess(String msg){
        return new JsonData(0,null,msg);
    }

    /**
     * 失败，固定状态码
     * failed,unchangeable -1 code and return error message
     * @param msg
     * @return
     */
    public static JsonData buildError(String  msg){
        return new JsonData(-1, null,msg);
    }


    /**
     * 失败，自定义错误码和信息
     * failed, changeable code and message
     * @param code
     * @param msg
     * @return
     */
    public static JsonData buildError(Integer code , String  msg){
        return new JsonData(code ,null,msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
