package com.ego.item.pojo;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.pojo
 * @version:1.0
 */
public class ParamItemNode {
    private String k;
    private String v;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "ParamItemNode{" +
                "k='" + k + '\'' +
                ", v='" + v + '\'' +
                '}';
    }
}
