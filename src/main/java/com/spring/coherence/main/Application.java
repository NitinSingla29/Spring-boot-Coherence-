package com.spring.coherence.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

import com.spring.coherence.configuration.CoherenceConfiguration;
import com.tangosol.net.CacheFactory;

@SpringBootApplication
@Import(CoherenceConfiguration.class)
public class Application implements CommandLineRunner {

	@Autowired
	private CacheManager cacheManger;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		String key = "K1";
		String value = "Hello World!";

//		CacheFactory.ensureCluster();
//		NamedCache cache = CacheFactory.getCache("hello-example");

		Cache cache = cacheManger.getCache("hello-example");
//		cache.put(key, value);
		System.out.println((String) cache.get(key).get());
	}
}
