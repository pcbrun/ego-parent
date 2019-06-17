package com.ego.redis.dao.impl;

import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Auther:pcb
 * @Date:19/5/31
 * @Description:com.ego.redis.dao.impl
 * @version:1.0
 */
@Repository
public class JedisDaoImpl implements JedisDao {
    @Resource
    JedisCluster jedisClients;

    @Override
    public boolean exists(String key) {
        return jedisClients.exists(key);
    }

    @Override
    public long del(String key) {
        return jedisClients.del(key);
    }

    @Override
    public String set(String key,String value,int seconds) {
        String setResult = jedisClients.set(key, value);
        if(seconds>0){
            jedisClients.expire(key, seconds);
        }
        return setResult;
    }

    @Override
    public String get(String key) {
        return jedisClients.get(key);
    }
}
