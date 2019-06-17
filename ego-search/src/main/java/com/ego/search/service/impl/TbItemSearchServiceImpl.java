package com.ego.search.service.impl;

import com.ego.commons.pojo.TbItemChild;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemSearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther:pcb
 * @Date:19/6/8
 * @Description:com.ego.search.service.impl
 * @version:1.0
 */
@Service
public class TbItemSearchServiceImpl implements TbItemSearchService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Resource
    private CloudSolrClient solrClient;

    @Override
    public void init() throws IOException, SolrServerException {
        // 清空solr
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
        // 查询所有正常的商品
        List<TbItem> ListTbItem = tbItemDubboServiceImpl.selAllByStatus((byte) 1);
        for (TbItem tbItem:ListTbItem) {
            // 商品对应的类目信息
            TbItemCat tbItemCat = tbItemCatDubboServiceImpl.selByID(tbItem.getCid());
            // 商品对应的描述信息
            TbItemDesc tbItemDesc = tbItemDescDubboServiceImpl.selByItemId(tbItem.getId());

            // 将数据初始化到solr中
            // addField将为该字段的任何现有值添加另一个值
            // setField将覆盖已存在的任何内容
            SolrInputDocument doc=new SolrInputDocument();
            doc.setField("id",tbItem.getId());
            doc.setField("item_title",tbItem.getTitle());
            doc.setField("item_sell_point",tbItem.getSellPoint());
            doc.setField("item_price",tbItem.getPrice());
            doc.setField("item_image",tbItem.getImage());
            doc.setField("item_category_name",tbItemCat.getName());
            doc.setField("item_desc",tbItemDesc.getItemDesc());
            doc.setField("item_updated",tbItem.getUpdated());

            solrClient.add(doc);
        }
        solrClient.commit();
    }

    @Override
    public Map<String, Object> selByQuery(String query, int page, int rows) throws IOException, SolrServerException {
        // 设置查询条件
        SolrQuery params=new SolrQuery();
        // 设置分页条件
        params.setStart(rows*(page-1));
        params.setRows(rows);
        // 设置查询体
        params.setQuery("item_keywords:"+query);
        // 设置高亮
        //开启高亮
        params.setHighlight(true);
        // 设置高亮内容
        params.addHighlightField("item_title");
        // 设置高亮内容前后缀
        params.setHighlightSimplePre("<span style='color:red'>");
        params.setHighlightSimplePost("</span>");
        // 设置排序
        params.addSort("item_updated", SolrQuery.ORDER.desc);

        // 获取查询到的响应内容
        QueryResponse response=solrClient.query(params);

        List<TbItemChild> listChild=new ArrayList<>();

        //获取未高亮内容
        SolrDocumentList listResult= response.getResults();
        //System.out.println(listResult);

        // 获取高亮内容
        Map<String, Map<String, List<String>>> hl = response.getHighlighting();

        for(SolrDocument solrDocument:listResult){
            TbItemChild child=new TbItemChild();

            child.setId(Long.parseLong(solrDocument.getFieldValue("id").toString()));

            List<String> listHL = hl.get(solrDocument.getFieldValue("id")).get("item_title");
            if(listHL!=null&&listHL.size()>0){
                child.setTitle(listHL.get(0));
            }else{
                child.setTitle(solrDocument.getFieldValue("item_title").toString());
            }

            child.setPrice((long)solrDocument.getFieldValue("item_price"));

            Object image = solrDocument.getFieldValue("item_image");
            child.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));

            child.setSellPoint(solrDocument.getFieldValue("item_sell_point").toString());

            listChild.add(child);
        }

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("itemList",listChild);
        resultMap.put("totalPages",listResult.getNumFound()%rows==0?listResult.getNumFound()/rows:listResult.getNumFound()/rows+1);

        return resultMap;
    }

    @Override
    public int add(Map<String,Object> map,String itemDesc) throws IOException, SolrServerException {
        SolrInputDocument doc=new SolrInputDocument();

        doc.setField("id",map.get("id"));
        doc.setField("item_title",map.get("title"));
        doc.setField("item_sell_point",map.get("sellPoint"));
        doc.setField("item_price",map.get("price"));
        doc.setField("item_image",map.get("image"));
        doc.setField("item_category_name",tbItemCatDubboServiceImpl.selByID((Integer)map.get("cid")).getName());
        doc.setField("item_desc",itemDesc);
        long milliSecond=Long.parseLong(map.get("updated").toString());
        Date date =new Date();
        date.setTime(milliSecond);
        doc.setField("item_updated",date);

        UpdateResponse response = solrClient.add(doc);
        solrClient.commit();
        if(response.getStatus()==0){
            return 1;
        }
        return 0;
    }

    @Override
    public int del(long id) throws IOException, SolrServerException {
        UpdateResponse response = solrClient.deleteById(id+"");
        solrClient.commit();
        if(response.getStatus()==0){
            return 1;
        }
        return 0;
    }
}
