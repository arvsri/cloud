package com.arvindsrivastava.cloud.google;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GoogleStorageServiceIT {

	private static final String bucketName = "arvsrigcp1user-my-second-bucket";
	private static final String objectName = "MyUniqueObj" + (int)(Math.random() * 100);

	@Test
	public void verifyStoreAndGetContent() {
		GoogleStorageService storageService = new GoogleStorageService();
		String storageContent = "My storage Content";
		
		String resourceURI = storageService.storeContent(bucketName, objectName, storageContent.getBytes());
		System.out.println("Object stored at " + resourceURI);
		
		String retrivedContent = new String(storageService.getContent(resourceURI));
		System.out.println(retrivedContent);
		
		assertEquals(storageContent, retrivedContent);
	}

}
