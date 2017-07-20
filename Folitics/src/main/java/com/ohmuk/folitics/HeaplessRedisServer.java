package com.ohmuk.folitics;

import java.io.IOException;
import java.util.List;

import redis.embedded.Redis;
import redis.embedded.RedisServer;

import com.google.common.collect.Lists;

/**
 * 
 */
public class HeaplessRedisServer extends RedisServer implements Redis {

	public HeaplessRedisServer(Integer port) throws IOException {
		super(port);
		List<String> temp = Lists.newArrayList();
		temp.addAll(this.args);
		temp.add("--maxheap");
		temp.add("1G");
		this.args = temp;
	}
}
