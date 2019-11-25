package com.arvindsrivastava.cloud.google;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


public class GoogleStorageService {
	
	private static Storage storage = StorageOptions.getDefaultInstance().getService();
	
	public String storeContent(String bucketName, String objectName, byte[] content) {
		BlobId blobId = BlobId.of(bucketName, objectName);
	
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
		Blob blob = storage.create(blobInfo, content);

		return buildURI(blob.getBlobId());
	}
	
	public byte[] getContent(String resourceURI) {
		String[] parsedURI = parseURI(resourceURI);
		return getContent(parsedURI[0], parsedURI[1]);
	}

	public byte[] getContent(String bucketName, String objectName) {
		return storage.get(BlobId.of(bucketName, objectName)).getContent(new BlobSourceOption[0]);
	}

	private String[] parseURI(String resourceURI) {
		// resource URI format = gs://<Bucket Name>/<Object Name>
		String[] tokens = resourceURI.split("/");
		return new String[] { tokens[tokens.length - 2], tokens[tokens.length - 1] };
	}

	private String buildURI(BlobId blobId) {
		return "gs://" + blobId.getBucket() + "/" + blobId.getName();
	}


}
