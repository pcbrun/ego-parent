package com.ego.search.controller;

import com.ego.pojo.TbItem;
import com.ego.search.service.TbItemSearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther:pcb
 * @Date:19/6/8
 * @Description:com.ego.search.controller
 * @version:1.0
 */
@Controller
public class TbItemSearchController {
    @Resource
    private TbItemSearchService tbItemSearchServiceImpl;

    /**
     * solr初始化
     * @return
     */
    @RequestMapping(value = "/solr/init",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String init(){
        long start = System.currentTimeMillis();
        try {
            tbItemSearchServiceImpl.init();
            long end=System.currentTimeMillis();
            return "初始化总时间："+(end-start)/1000+"秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "初始化失败";
        }
    }

    /**
     * 商品搜索功能
     * @param model
     * @param q
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.html")
    public String search(Model model,String q, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int rows){
        try {
            q=new String(q.getBytes("iso-8859-1"),"utf-8");
            Map<String, Object> map = tbItemSearchServiceImpl.selByQuery(q, page, rows);
            model.addAttribute("query",q);
            model.addAttribute("itemList",map.get("itemList"));
            model.addAttribute("totalPages",map.get("totalPages"));
            model.addAttribute("page",page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }

    /**
     * solr数据新增
     * @param map
     * @return
     */
    @RequestMapping("/solr/add")
    @ResponseBody
    public int add(@RequestBody Map<String,Object> map){
        try {
            return tbItemSearchServiceImpl.add((LinkedHashMap) map.get("tbItem"),map.get("itemDesc").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * solr数据删除
     * @param id
     * @return
     */
    @RequestMapping("/solr/del")
    @ResponseBody
    public int add(@RequestBody long id){
        try {
            return tbItemSearchServiceImpl.del(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
