package com.ego.search.service;

import com.ego.pojo.TbItem;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/6/8
 * @Description:com.ego.search.service
 * @version:1.0
 */
public interface TbItemSearchService {
    /**
     * 商品搜索solr初始化
     */
    void init() throws IOException, SolrServerException;

    /**
     * 商品搜索分页查询
     * @param query
     * @param page
     * @param rows
     * @return
     */
    Map<String,Object> selByQuery(String query,int page,int rows) throws IOException, SolrServerException;

    /**
     * solr新增
     * @param map,itemDesc
     * @return
     */
    int add(Map<String,Object> map,String itemDesc) throws IOException, SolrServerException;

    /**
     * solr删除
     * @param id
     * @return
     */
    int del(long id) throws IOException, SolrServerException;
}
