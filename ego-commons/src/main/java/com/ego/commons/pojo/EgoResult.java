package com.ego.commons.pojo;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.commons.pojo
 * @version:1.0
 */
public class EgoResult {
    // 状态码
    private int status;
    // 异常信息
    private Object data;
    // 登录信息
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
