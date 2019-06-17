package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public int insTbItemDesc(TbItemDesc itemDesc) {
        return tbItemDescMapper.insertSelective(itemDesc);
    }

    @Override
    public TbItemDesc selByItemId(long itemId) {
        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }
}
