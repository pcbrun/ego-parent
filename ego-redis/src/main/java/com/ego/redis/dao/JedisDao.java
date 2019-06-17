package com.ego.redis.dao;

/**
 * @Auther:pcb
 * @Date:19/5/31
 * @Description:com.ego.redis.dao
 * @version:1.0
 */
public interface JedisDao {
    /**
     * 判断key是否已存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 删除值
     * @param key
     * @return
     */
    long del(String key);

    /**
     * 设置值和过期时间
     * @param key
     * @return
     */
    String set(String key,String value,int seconds);

    /**
     * 获取值
     * @param key
     * @return
     */
    String get(String key);
}
