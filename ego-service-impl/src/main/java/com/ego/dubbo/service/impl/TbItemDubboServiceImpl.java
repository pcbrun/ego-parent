package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Resource
    private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        // 分页查询结果封装
        PageInfo<TbItem> pi = new PageInfo<>(list);
        // 放入到数据网格中
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public TbItem selById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TbItemParamItem selParamByItemId(long itemId) {
        TbItemParamItemExample example=new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public TbItemDesc selDescByItemId(long itemId) {
        return tbItemDescMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public int updItemStatus(TbItem tbItem) {
        // 只更新model中不为null的字段
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int insItem(TbItem tbItem) {
        return tbItemMapper.insertSelective(tbItem);
    }

    @Override
    public int insItemDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception {
        int result = 0;
        try {
            result = tbItemMapper.insertSelective(tbItem);
            result += tbItemDescMapper.insertSelective(tbItemDesc);
            result+=tbItemParamItemMapper.insertSelective(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == 3) {
            return 1;
        } else {
            throw new Exception("新增失败，数据回滚");
        }
    }

    @Override
    public int updItem(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception {
        int result=0;
        try {
            result+=tbItemMapper.updateByPrimaryKeySelective(tbItem);
            result+=tbItemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
            result+=tbItemParamItemMapper.updateByPrimaryKeyWithBLOBs(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result==3){
            return 1;
        } else{
            throw new Exception("修改失败，数据回滚");
        }
    }

    @Override
    public List<TbItem> selAllByStatus(byte status) {
        TbItemExample example=new TbItemExample();
        example.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(example);
    }
}
