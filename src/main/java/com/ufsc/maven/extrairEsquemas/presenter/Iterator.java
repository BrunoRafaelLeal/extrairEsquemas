package com.ufsc.maven.extrairEsquemas.presenter;

import com.ufsc.maven.extrairEsquemas.util.RedisConnector;
import com.ufsc.maven.extrairEsquemas.util.MongoConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

public class Iterator {
	DB db;
	DBCollection coll;
	DBObject dbObj;
	/*
	 * Comando para ler os valores do redis e detectar se são ou não JSON.
	 * Em si ele percorre todas as chaves do redis e, para cada chave, ele detecta se a chave-valor é do tipo JSON
	 * Caso seja ela será adicionada ao documento Json (essa é a parte que eu irei estruturar)
	 * Em caso de não ser ela será gerada em um esquema de chave-valor
	 */
	public void readRedis() {
		Jedis jedis = new Jedis("localhost");

		ScanResult<String> scanResult = jedis.scan("0");
		String nextCursor = scanResult.getStringCursor();
		JSONParser parser = new JSONParser();
		int counter = 0;
		while (true) { 
			nextCursor = scanResult.getStringCursor();
			List<String> keys = scanResult.getResult();
			for (counter = 0; counter < keys.size(); counter++) {
				if(counter == keys.size()) {
					break;
				}				
				try {
					String key = keys.get(counter);
					JSONObject json = (JSONObject) parser.parse(jedis.get(key));
					documentoJson(json);					
					//System.out.println("Added to function 'documentoJson'");
				} catch (ParseException e) {
					System.out.println("Not a valid JSON");
				}
			}
			if (nextCursor.equals("0")) {
				break;
			}
			scanResult = jedis.scan(nextCursor);
		}
		jedis.close();
	}
	//Adiciona o documento 	JSONObject em um JSONArray 
	public JSONArray documentoJson(JSONObject json) {
		buildRawSchema(json);
		JSONObject jObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		jArray.add(json);
		//System.out.println(jArray);

		//jObject.put("JSON Document", jArray);
		
		return jArray;
	}
	public JSONObject buildRawSchema(JSONObject json) {
		/*A função percorre todas as chaves do json, detectando qual é o tipo do valor, a cada vez que detecta o tipo
		 * do valor	ele substitui o que está escrito para o tipo do valor (exemplo: Nome: "String") no json.
		 * O que provavelmente precisa: método para salvar a variável (possivelmente é o método do redis no qual já
		 * foi listado), após isso acho que seria feito a interface.
		*/
		for(Object key : json.keySet() ) {
            Object value = json.get(key);
            //System.out.println(key + " = " + value);
            if (value instanceof JSONObject) {
            	buildRawSchema((JSONObject) value);
            }else if (value instanceof JSONArray) {
            	buildRawSchema((JSONObject) value);
            }else if (value instanceof Number) {
            	json.replace(key, "Number");
            }else  if (value instanceof Boolean) {
            	json.replace(key, "Boolean");

            }else  if (value instanceof String) {
            	json.replace(key, "String");

            }else{
            json.replace(key, "null");
            }
		}
		insertJSON(json);
		return json;
	}
	public DBCollection insertJSON(JSONObject json) {
		MongoConnector mongo = new MongoConnector();
		JSONParser parser = new JSONParser();
			mongo.createCollection(dbObj);
			coll.insert(dbObj);
			return coll;
			
	}
}
