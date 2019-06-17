package com.ego.item.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.pojo.ParamItemNode;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.service.impl
 * @version:1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.itemParamItem.key}")
    String itemParamItemKey;

    @Override
    public String showParam(long itemId) {
        String key=itemParamItemKey+itemId;
        if(jedisDaoImpl.exists(key)){
            String param = jedisDaoImpl.get(key);
            if(param!=null&&!param.equals("")){
                return param;
            }
        }

        TbItemParamItem tbItemParamItem = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
        String paramData = tbItemParamItem.getParamData();
        List<ParamItem> listParam= JsonUtils.jsonToList(paramData,ParamItem.class);

        StringBuffer sb=new StringBuffer();
        for(ParamItem paramItem:listParam){
            sb.append("<table width='500px' cellpadding='10px' cellspacing='10px' style='color:gray;'>");
            for(int i=0;i<paramItem.getParams().size();i++){
                ParamItemNode paramItemNode = paramItem.getParams().get(i);

                if(i==0){
                    sb.append("<tr><td  align='right' width='30%'>"+paramItem.getGroup()+"</td>");
                    sb.append("<td  align='right' width='30%'>"+paramItemNode.getK()+"</td>");
                    sb.append("<td >"+paramItemNode.getV()+"</td></tr>");
                }else{
                    sb.append("<tr><td></td>");
                    sb.append("<td  align='right' width='30%'>"+paramItemNode.getK()+"</td>");
                    sb.append("<td>"+paramItemNode.getV()+"</td></tr>");
                }
            }

            sb.append("</table>");
            sb.append("<hr style='color:gray;'/>");
        }

        jedisDaoImpl.set(key,sb.toString(),60*60*24*10);

        return sb.toString();
    }
}
