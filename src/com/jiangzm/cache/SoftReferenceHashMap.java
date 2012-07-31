package com.jiangzm.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class SoftReferenceHashMap<K, V> {
	HashMap<K, SoftReference<V>> map = new HashMap<K, SoftReference<V>>();

	public V put(K key, V value) {
		SoftReference<V> old = map.put(key, new SoftReference<V>(value));
		if (old == null)
			return null;
		return old.get();
	}

	public V get(K key) {
		SoftReference<V> val = map.get(key);
		if (val == null)
			return null;
		V ret = val.get();
		if (ret == null)
			map.remove(key);
		return ret;
	}
}
