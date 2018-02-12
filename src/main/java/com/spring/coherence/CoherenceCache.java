package com.spring.coherence;

import com.tangosol.net.NamedCache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

public class CoherenceCache implements Cache {

	private NamedCache namedCache;

	public CoherenceCache(NamedCache namedCache) {
		this.namedCache = namedCache;
	}

	protected Object lookup(Object key) {
		return namedCache.get(key);
	}

	public String getName() {
		return namedCache.getCacheName();
	}

	public Object getNativeCache() {
		return this.namedCache;
	}

	public <T> T get(Object o, Callable<T> callable) {
		throw new UnsupportedOperationException();
	}

	public void put(Object key, Object value) {
		this.namedCache.put(key, value);
	}

	public ValueWrapper putIfAbsent(Object o, Object o1) {
		throw new UnsupportedOperationException();
	}

	public void evict(Object key) {
		this.namedCache.remove(key);
	}

	public void clear() {
		this.namedCache.clear();
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = lookup(key);
		return toValueWrapper(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object value = lookup(key);
		if (value != null && type != null && !type.isInstance(value)) {
			throw new IllegalStateException(
					"Cached value is not of required type [" + type.getName()
							+ "]: " + value);
		}
		return (T) value;
	}
	
	protected Cache.ValueWrapper toValueWrapper(Object storeValue) {
		return (storeValue != null ? new SimpleValueWrapper(storeValue) : null);
	}
}
