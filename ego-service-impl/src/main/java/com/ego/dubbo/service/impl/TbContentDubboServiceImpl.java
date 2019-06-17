package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Resource
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIDataGrid selByPage(long categoryId, int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page,rows);
        // 创建查询体
        TbContentExample example=new TbContentExample();
        if(categoryId!=0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExample(example);
        // 封装查询结果
        PageInfo<TbContent> pi=new PageInfo<>(list);

        EasyUIDataGrid dataGrid=new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public int insContent(TbContent tbContent) {
        return tbContentMapper.insertSelective(tbContent);
    }

    @Override
    public List<TbContent> selByCount(int count, boolean isSort) {
        TbContentExample example=new TbContentExample();
        // 排序
        if(isSort){
            example.setOrderByClause("updated desc");
        }
        // 分页
        if(count!=0){
            PageHelper.startPage(1,count);
            List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
            PageInfo<TbContent> pi=new PageInfo<>(list);
            return pi.getList();
        }
        return tbContentMapper.selectByExampleWithBLOBs(example);
    }
}
