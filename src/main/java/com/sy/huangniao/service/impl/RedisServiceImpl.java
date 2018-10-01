package com.sy.huangniao.service.impl;

import com.alibaba.fastjson.JSON;
import com.sy.huangniao.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by huchao
 */
@Slf4j
@Service
public class RedisServiceImpl implements IRedisService {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public <K, V> V get(K key, Class<V> V) {
        try{
            Object objFromRedis = redisTemplate.opsForValue().get(key);
            if(null != objFromRedis) {
                return (V) objFromRedis;
            }
        } catch (Exception e) {
            log.error("从redis获取地址对象出错,key:{}", key, e);
        }
        
        return null;
    }

    @Override
    public <K, V> void set(K k, V v) {
        try{
            redisTemplate.opsForValue().set(k, v);
            log.info("向redis中保存数据成功，key:{}，val:{}", k, JSON.toJSON(v));
        } catch (Exception e) {
            log.error("向redis保存地址对象出错, key:{}, combineAddr:{}", k, JSON.toJSON(v), e);
        }
    }

    public <K, V> void set(K k, V v, long exprirTime, TimeUnit timeUnit) {
        try{
            redisTemplate.opsForValue().set(k, v, exprirTime, timeUnit);
            log.info("向redis中保存数据成功，key:{}，val:{}", k, JSON.toJSON(v));
        } catch (Exception e) {
            log.error("向redis保存地址对象出错, key:{}, combineAddr:{}", k, JSON.toJSON(v), e);
        }
    }

    @Override
    public <K> void del(K k) {
        try{
            redisTemplate.opsForValue().getOperations().delete(k);
            log.info("从redis中删除数据成功，key:{}", k);
        } catch (Exception e) {
            log.error("从redis中删除数据成功出错, key:{}", k, e);
        }
    }

}
