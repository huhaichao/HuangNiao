package com.sy.huangniao.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by yanpeiling on 2018.4.17.
 */
public interface IRedisService {
    <K, V> V get(K k, Class<V> v);

    <K, V> void set(K k, V v);

    <K, V> void set(K k, V v, long exprirTime, TimeUnit timeUnit);

    <K> void del(K k);

    <K> List<K> getKeys(K k);
}
