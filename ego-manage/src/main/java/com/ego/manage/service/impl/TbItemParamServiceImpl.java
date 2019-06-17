package com.ego.manage.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.pojo.TbItemParamChild;
import com.ego.manage.service.TbItemCatService;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.manage.service.impl
 * @version:1.0
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {
    @Reference
    private TbItemParamDubboService tbItemParamDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;

    @Override
    public EasyUIDataGrid showPage(int page, int rows) {
        EasyUIDataGrid dataGrid = tbItemParamDubboServiceImpl.showPage(page, rows);
        List<TbItemParam> list = (List<TbItemParam>) dataGrid.getRows();
        List<TbItemParamChild> listChild=new ArrayList<>();
        for (TbItemParam param:list){
            TbItemParamChild paramChild = new TbItemParamChild();
            paramChild.setId(param.getId());
            paramChild.setItemCatId(param.getItemCatId());
            paramChild.setParamData(param.getParamData());
            paramChild.setCreated(param.getCreated());
            paramChild.setUpdated(param.getUpdated());
            paramChild.setItemCatName(tbItemCatDubboServiceImpl.selByID(param.getItemCatId()).getName());

            listChild.add(paramChild);
        }

        dataGrid.setRows(listChild);
        return dataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        return tbItemParamDubboServiceImpl.delByIds(ids);
    }

    @Override
    public EgoResult showParam(long itemCatId) {
        EgoResult er=new EgoResult();
        TbItemParam tbItemParam = tbItemParamDubboServiceImpl.showParam(itemCatId);
        if(tbItemParam!=null){
            er.setStatus(200);
            er.setData(tbItemParam);
        }
        return er;
    }

    @Override
    public EgoResult insert(TbItemParam tbItemParam) {
        Date date=new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int result = tbItemParamDubboServiceImpl.insParam(tbItemParam);
        EgoResult er=new EgoResult();
        if(result>0){
            er.setStatus(200);
        }
        return er;
    }
}
