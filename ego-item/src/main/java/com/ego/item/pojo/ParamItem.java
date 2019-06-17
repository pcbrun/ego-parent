package com.ego.item.pojo;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.pojo
 * @version:1.0
 */
public class ParamItem {
    private String group;
    private List<ParamItemNode> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ParamItemNode> getParams() {
        return params;
    }

    public void setParams(List<ParamItemNode> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ParamItem{" +
                "group='" + group + '\'' +
                ", params=" + params +
                '}';
    }
}
