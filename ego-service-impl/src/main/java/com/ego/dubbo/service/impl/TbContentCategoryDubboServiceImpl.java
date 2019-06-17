package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selByPid(long pid) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    public int insTbContentCategory(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.insertSelective(tbContentCategory);
    }

    @Override
    public int updTbContentCategory(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    @Override
    public TbContentCategory selById(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }
}
