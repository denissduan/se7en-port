package com.se7en.common;

import net.sf.ehcache.*;

/**
 * Created by Administrator on 2017/7/6.
 */
public class EhCache {

    private static volatile EhCache entity;

    private EhCache(){
    }

    public static EhCache getInstance(){
        synchronized (EhCache.class){
            if(entity == null){
                synchronized (EhCache.class){
                    entity = new EhCache();
                }
            }
        }
        return entity;
    }

    public Cache getHmmCache(){
        final CacheManager cacheManager = CacheManager.getInstance();
        Cache hmmCache = cacheManager.getCache("hmm");

        return hmmCache;
    }

}
