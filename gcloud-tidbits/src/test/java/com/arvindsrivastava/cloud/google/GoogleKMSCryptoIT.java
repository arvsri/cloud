package com.arvindsrivastava.cloud.google;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class GoogleKMSCryptoIT {
	
	@Test
	public void verifyEncrptyDecrypt() throws Exception {
		// Key resource id
		String kmsKeyResourceId = "projects/my-user-project-2-243606/locations/europe-west2/keyRings/my-first-keyring/cryptoKeys/my-first-key";
		
		String secretText = "This is my secret";
		GoogleKMSCrypto crypto = new GoogleKMSCrypto();
		byte[] cipherText = crypto.encrypt(kmsKeyResourceId, secretText.getBytes());
		
		System.out.println("Cipher Text : " + new String(cipherText));
		assertNotEquals("Secret could not be encrypted!", secretText,new String(cipherText));
		
		byte[] plainText = crypto.decrypt(kmsKeyResourceId, cipherText);
		assertEquals("Encrypted secret could not be decrypted!", secretText,new String(plainText));
		
	}
	
}
