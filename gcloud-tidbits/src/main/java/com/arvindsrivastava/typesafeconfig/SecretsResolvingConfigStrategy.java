package com.arvindsrivastava.typesafeconfig;

import java.io.IOException;

import com.arvindsrivastava.cloud.google.GoogleKMSCrypto;
import com.arvindsrivastava.cloud.google.GoogleStorageService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.DefaultConfigLoadingStrategy;

public class SecretsResolvingConfigStrategy extends DefaultConfigLoadingStrategy{
	
    @Override
	public Config parseApplicationConfig(ConfigParseOptions parseOptions) {
		resolveSecrets();
		return super.parseApplicationConfig(parseOptions);
	}

    void resolveSecrets() {
    	for(String property : System.getProperties().stringPropertyNames()) {
    		if(property.endsWith("password-path")) {
    			String baseProperty = property.substring(0,property.lastIndexOf("-path"));
    			resolveSecretsViaKMS(baseProperty);
    		}
    	}
    	ConfigFactory.invalidateCaches();
	}

	void resolveSecretsViaKMS(String baseProperty) {
		try {
			String kmsResourcePath = System.getProperties().getProperty(baseProperty + "-key");
			String encryptedSecretPath = System.getProperties().getProperty(baseProperty + "-path");

			byte[] cipherText = new GoogleStorageService().getContent(encryptedSecretPath);
			byte[] plainText = new GoogleKMSCrypto().decrypt(kmsResourcePath, cipherText);

			System.setProperty(baseProperty, new String(plainText));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
