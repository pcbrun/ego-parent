package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.item.pojo
 * @version:1.0
 */
public class PortalMenuNode {
    // 商品分类数据中包含的数据
    private String u;
    private String n;
    private List<Object> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<Object> getI() {
        return i;
    }

    public void setI(List<Object> i) {
        this.i = i;
    }
}
