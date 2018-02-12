package com.spring.coherence.configuration;

import com.spring.coherence.CoherenceCache;
import com.tangosol.net.*;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
public class CoherenceConfiguration {

  @Bean
  public CacheManager cacheManager() {
    CoherenceCacheManager cacheManager = new CoherenceCacheManager();
    return cacheManager;
  }
  
  @PreDestroy
  public void preDestroy() {
	  CacheFactory.shutdown();
  }
  
  private class CoherenceCacheManager implements CacheManager {
    public CoherenceCacheManager() {
      CacheFactory.ensureCluster();
    }

    public Cache getCache(String cacheName) {
      NamedCache namedCache =  CacheFactory.getCache(cacheName);
      return new CoherenceCache(namedCache);
    }

    public Collection<String> getCacheNames() {
      throw new UnsupportedOperationException();
    }
  }
}
