package com.se7en.common.ai.ml;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * Created by Administrator on 17-6-17.
 */
public final class MLCacheUtil {

    private MLCacheUtil(){
    }

    public static Cache getCache(){
        CacheManager singletonManager = CacheManager.create();
        Cache ret = singletonManager.getCache("hmm");

        return ret;
    }

}
