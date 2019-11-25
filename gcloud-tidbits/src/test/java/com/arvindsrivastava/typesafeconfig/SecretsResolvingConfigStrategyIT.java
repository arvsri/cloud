package com.arvindsrivastava.typesafeconfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;

import com.arvindsrivastava.cloud.google.GoogleKMSCrypto;
import com.arvindsrivastava.cloud.google.GoogleStorageService;

public class SecretsResolvingConfigStrategyIT {

	private final String bucketName = "arvsrigcp1user-my-second-bucket";
	private final String objectName = "MyUniqueObj" + (int) (Math.random() * 100);

	private final GoogleStorageService storageService = new GoogleStorageService();
	private final GoogleKMSCrypto kmsService = new GoogleKMSCrypto();
	
	private final String super_secret = "This is my super secret";
	
	private String encryptedSecretPath = "gs://arvsrigcp1user-my-second-bucket/MyUniqueObj85";
	private String kmsKeyResourceId = "projects/my-user-project-2-243606/locations/europe-west2/keyRings/my-first-keyring/cryptoKeys/my-first-key";
	
	
	public void setup() throws IOException {
		this.encryptedSecretPath = storageService.storeContent(bucketName, objectName,
				kmsService.encrypt(kmsKeyResourceId, super_secret.getBytes()));
		assertNotEquals(super_secret, new String(storageService.getContent(this.encryptedSecretPath)));
	}
	
	
	@Test
	public void verifyResolveSecrets() {
		System.setProperty("production.db.password-path", this.encryptedSecretPath);
		System.setProperty("production.db.password-key", this.kmsKeyResourceId);
		
		assertNull(System.getProperty("production.db.password"));
		
		new SecretsResolvingConfigStrategy().resolveSecrets();
		assertEquals(super_secret,System.getProperty("production.db.password"));
	}
	
	public void tearDown() {
		
	}
	
}
