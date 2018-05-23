package com.ufsc.maven.extrairEsquemas.presenter;

import com.ufsc.maven.extrairEsquemas.presenter.Iterator;

public class Main extends Iterator{
	public static void main(String[] args) {
		Iterator iterator = new Iterator();
		iterator.startServer();	
		//iterator.listAllKeys();
		iterator.readRedis();
	}
}
