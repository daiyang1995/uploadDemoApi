package com.qucai.kp.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTool {

	private static JedisPool jedisPool;
	
	static {
		InputStream in = RedisTool.class
				.getResourceAsStream("/jedis.properties");
		try {
			Properties properties = new Properties();
			properties.load(in);
			String host = properties.getProperty("redis.host");
			int port = Integer.valueOf(properties.getProperty("redis.port"));
			String password = properties.getProperty("redis.password");
			int maxTotal = Integer.valueOf(properties.getProperty("redis.maxTotal"));
			int maxIdle = Integer.valueOf(properties.getProperty("redis.maxIdle"));
			int maxWaitMillis = Integer.valueOf(properties.getProperty("redis.maxWaitMillis"));
			int timeout = Integer.valueOf(properties.getProperty("redis.timeout"));
			boolean testOnBorrow = Boolean.valueOf(properties.getProperty("redis.testOnBorrow"));

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWaitMillis);
			config.setTestOnBorrow(testOnBorrow);
			jedisPool = new JedisPool(config, host, port, timeout, password);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean exists(String key) {
		Jedis jds = jedisPool.getResource();
		boolean rs = jds.exists(key);
		jds.close();
		return rs;
	}
	
	public static Set<String> keys(String key) {
		Jedis jds = jedisPool.getResource();
		Set<String> rs = jds.keys(key);
		jds.close();
		return rs;
	}
	
	public static Long del(String key) {
		Jedis jds = jedisPool.getResource();
		Long rs = jds.del(key);
		jds.close();
		return rs;
	}
	
	public static Long del(String... keys) {
		Jedis jds = jedisPool.getResource();
		Long rs = jds.del(keys);
		jds.close();
		return rs;
	}
	
	public static void expire(String key, int seconds) {
		Jedis jds = jedisPool.getResource();
		jds.expire(key, seconds);
		jds.close();
	}
	
	public static void set(String key, String value) {
		Jedis jds = jedisPool.getResource();
		jds.set(key, value);
		jds.close();
	}
	
	public static String get(String key) {
		Jedis jds = jedisPool.getResource();
		String value = jds.get(key);
		jds.close();
		return value;
	}
	
	public static void hset(String key, String field, String value) {
		Jedis jds = jedisPool.getResource();
		jds.hset(key, field, value);
		jds.close();
	}
	
	public static String hget(String key, String field) {
		Jedis jds = jedisPool.getResource();
		String value = jds.hget(key, field);
		jds.close();
		return value;
	}
	
	public static Map<String, String> hgetAll(String key) {
		Jedis jds = jedisPool.getResource();
		Map<String, String> m = jds.hgetAll(key);
		jds.close();
		return m;
	}
	
	public static void flushDB() {
		Jedis jds = jedisPool.getResource();
		jds.flushDB();
		jds.close();
	}
	
	public static void hdel(String key, String field) {
		Jedis jds = jedisPool.getResource();
		jds.hdel(key, field);
		jds.close();
	}

}