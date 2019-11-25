package com.arvindsrivastava.cloud.google;

import java.io.IOException;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class GoogleKMSCrypto {
	
	public byte[] encrypt(String cryptoKeyResourceId, byte[] plaintext) throws IOException {

		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			// The resource name of the cryptoKey
			String resourceName = CryptoKeyName.parse(cryptoKeyResourceId).toString();
			
			// Encrypt the plaintext with Cloud KMS.
			EncryptResponse response = client.encrypt(resourceName, ByteString.copyFrom(plaintext));
			
			// Extract the ciphertext from the response.
			return response.getCiphertext().toByteArray();
		}

	}
	
	public byte[] decrypt(String cryptoKeyResourceId, byte[] ciphertext) throws IOException {

		try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {
			// The resource name of the cryptoKey
			String resourceName = CryptoKeyName.parse(cryptoKeyResourceId).toString();

			// Decrypt the ciphertext with Cloud KMS.
			DecryptResponse response = client.decrypt(resourceName, ByteString.copyFrom(ciphertext));

			// Extract the plaintext from the response.
			return response.getPlaintext().toByteArray();
		}

	}

	
	
}
