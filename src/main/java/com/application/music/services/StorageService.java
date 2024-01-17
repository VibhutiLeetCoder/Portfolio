package com.application.music.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class StorageService {
	
	private final AmazonS3 space;
	
	public StorageService() {
		
		
		AWSCredentialsProvider aswCredentials = new AWSStaticCredentialsProvider(
				new BasicAWSCredentials("DO00D648JQ78N9J4RBYF", "CQq4GTCsKLAnlR25BF5Ct7Hq9aWv6RAf2QzLuz7gBEQ")
				);
		
		space=  AmazonS3ClientBuilder
				.standard()
				.withCredentials(aswCredentials)
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("blr1.digitaloceanspaces.com", "blr1"))
				.build();
	}
	
	public List<String> getSongFileNames(){
		
		ListObjectsV2Result result= space.listObjectsV2("mymusicworld");
		List<S3ObjectSummary> objects= result.getObjectSummaries();
		
		return objects.stream().map(object->object.getKey()).collect(Collectors.toList());
		
	}
	
	public void uploadSongs(MultipartFile file) {
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		 
		try {
			space.putObject(new PutObjectRequest("mymusicworld",file.getOriginalFilename(),file.getInputStream(),metadata).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SdkClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
