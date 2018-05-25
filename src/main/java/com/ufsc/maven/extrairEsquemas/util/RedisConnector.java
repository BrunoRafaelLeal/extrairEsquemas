package com.ufsc.maven.extrairEsquemas.util;


import redis.clients.jedis.Jedis;

public class RedisConnector {

	private Jedis jedis;
	//Iniciação do server redis
	public void startServer() {
		Jedis jedis = new Jedis("localhost");
		System.out.println("Conexão feita com sucesso");
		jedis.close();
	}
	//Opção mais "lenta", porém mais efetiva para exibir todas as chaves
	public void listAllKeys() {
		Jedis jedis = new Jedis("localhost");
		System.out.println("Keys: " + jedis.keys("*"));
		jedis.close();
	}
	/* Deleta uma chave do servidor,
	 * Como o comando é rpop ele deleta apenas o ultimo elemento da lista baseada na chave dada
	 * Exemplo: Com chaves 'a', 'b', 'c', 'avião', caso seja feito um Iterator.delete(a), 
	 * irá resultar nas chaves 'a', 'b','c' .
	 * Caso queira deletar a primeira chave deve-se usar lpop, caso queira deletar todas as chaves encontradas use del
	*/
	public void delete(String key) {
		Jedis jedis = new Jedis("localhost");
		jedis.rpop(key);
		jedis.close();
	}
	//Torna o valor entregue como o valor da chave
	public void push(String key, String value) {
		Jedis jedis = new Jedis("localhost");
		jedis.set(key, value);
		jedis.close();
	}
	

}
