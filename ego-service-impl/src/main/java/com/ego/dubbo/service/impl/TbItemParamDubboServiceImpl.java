package com.ego.dubbo.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Resource
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public EasyUIDataGrid showPage(int page, int rows) {
        // 设置分页条件
        PageHelper.startPage(page, rows);
        // 设置查询的 SQL 语句 //XXXXExample() 设置了什么,相当于在sql 中 where 从句中添加条件
        // 如果表中有一个或一个以上的列是text类型. 生成的方法 xxxxxxxxWithBlobs()
        // 如果使用xxxxWithBlobs() 查询结果中包含带有text类的列,如果没有使用 withblobs() 方法不带有 text 类型
        List<TbItemParam> list=tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        // 根据程序员自己编写的 SQL 语句结合分页插件产生最终结果,封装到 PageInfo
        PageInfo<TbItemParam> pi=new PageInfo<>(list);
        // 将结果放入到数据网格
        EasyUIDataGrid dataGrid=new EasyUIDataGrid();
        dataGrid.setRows(pi.getList());
        dataGrid.setTotal(pi.getTotal());

        return dataGrid;
    }

    @Override
    public int delByIds(String ids) throws Exception {
        int result=0;
        String[] idsStr = ids.split(",");
        for (String id:idsStr) {
            result+=tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        if (result==idsStr.length){
            System.out.println(result);
            return 1;
        } else{
            throw new Exception("删除失败，可能原因：数据已经不存在");
        }
    }

    @Override
    public TbItemParam showParam(long itemCatId) {
        TbItemParamExample examp=new TbItemParamExample();
        examp.createCriteria().andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(examp);
        if(list!=null&&list.size()>0){
            // 要求每个类目只能有一个模板
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insParam(TbItemParam tbItemParam) {
        return tbItemParamMapper.insertSelective(tbItemParam);
    }

}
