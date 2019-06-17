package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Resource
    private TbOrderMapper tbOrderMapper;
    @Resource
    private TbOrderItemMapper tbOrderItemMapper;
    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Override
    public int insOrder(TbOrder tbOrder, List<TbOrderItem> listOrderItem, TbOrderShipping tbOrderShipping) throws Exception {
        int result=tbOrderMapper.insertSelective(tbOrder);
        for (TbOrderItem tbOrderItem : listOrderItem) {
            result+=tbOrderItemMapper.insertSelective(tbOrderItem);
        }
        result+=tbOrderShippingMapper.insertSelective(tbOrderShipping);
        if(result==2+listOrderItem.size()){
            return 1;
        }else {
            throw new Exception("订单创建失败");
        }
    }
}
