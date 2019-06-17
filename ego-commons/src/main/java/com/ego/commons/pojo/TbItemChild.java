package com.ego.commons.pojo;

import com.ego.pojo.TbItem;

/**
 * @Auther:pcb
 * @Date:19/6/8
 * @Description:com.ego.search.pojo
 * @version:1.0
 */
public class TbItemChild extends TbItem {
    private String[] images;

    // 判断商品库存是否充足
    private Boolean enough;

    public String[] getImages() {
        return images;
    }

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }

    public void setImages(String[] images) {

        this.images = images;
    }
}
