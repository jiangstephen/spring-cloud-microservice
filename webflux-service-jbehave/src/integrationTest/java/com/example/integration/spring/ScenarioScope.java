package com.example.integration.spring;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

@Component
public class ScenarioScope implements Scope {

	private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();
	
	@BeforeScenario
	public void startScenario(){
		cache.clear();
	}
	
	@Override
	public Object get(String key, ObjectFactory<?> objectFactory) {
		if(!cache.containsKey(key)){
			cache.putIfAbsent(key, objectFactory.getObject());
		}
		return cache.get(key);
	}

	@Override
	public String getConversationId() {
		return "scenario scope";
	}

	@Override
	public void registerDestructionCallback(String arg0, Runnable arg1) {
	}

	@Override
	public Object remove(String key) {
		return cache.remove(key);
	}

	@Override
	public Object resolveContextualObject(String arg0) {
		return null;
	}

}
