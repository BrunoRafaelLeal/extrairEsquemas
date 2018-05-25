package com.ufsc.maven.extrairEsquemas.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;

public class MongoConnector{
	    private MongoClient mongo;
   	   	private DB db;
   	   	private DBObject dbObj;
	public void startServer() {
		mongo = new MongoClient ("localhost");
		System.out.println("Conexão feita com sucesso");
		mongo.close();
	}
	public void listAllKeys() {
		mongo = new MongoClient ("localhost");
		System.out.println("Keys: " + mongo.listDatabaseNames() );
		mongo.close();
	}
	public void delete(String key) {
		mongo = new MongoClient ("localhost");
		mongo.dropDatabase(key);
		mongo.close();
		
	}
	public DBCollection createCollection(DBObject dbObj) {
		mongo = new MongoClient ("localhost");
        DBCollection coll = db.createCollection("mycol", dbObj);
        System.out.println("Coleção criada com sucesso");
        return coll;
	}
	
}