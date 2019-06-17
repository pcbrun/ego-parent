package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public TbItemParamItem selByItemId(long itemId) {
        TbItemParamItemExample example=new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> listTbItemParamItem = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(listTbItemParamItem!=null&&!listTbItemParamItem.equals("")){
            return listTbItemParamItem.get(0);
        }
        return null;
    }
}
