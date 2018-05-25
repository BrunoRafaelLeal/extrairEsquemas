package com.ufsc.maven.extrairEsquemas.presenter;

import com.ufsc.maven.extrairEsquemas.presenter.Iterator;
import com.ufsc.maven.extrairEsquemas.util.RedisConnector;
import com.ufsc.maven.extrairEsquemas.util.MongoConnector;

public class Main extends Iterator{
	public static void main(String[] args) {
		RedisConnector redis = new RedisConnector();
		Iterator iterator = new Iterator();
		MongoConnector mongo = new MongoConnector();
		redis.startServer();	
		mongo.startServer();
		//redis.listAllKeys();
		iterator.readRedis();
		
	}
}
