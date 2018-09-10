package com.devworker.kms.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.devworker.kms.dto.message.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

public class MessageClient {
	private static MessageClient messageClient = new MessageClient();
	
	public static MessageClient getInstance() {
		return messageClient;
	}
	
	CloseableHttpClient httpClient;
	Gson mapper = new Gson();
	
	private MessageClient() {
		httpClient = HttpClients.createDefault();
	}
	
	public MessageDto sendMessage(String url,MessageDto model) throws JsonProcessingException {
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type" , "application/json");
		String json = mapper.toJson(model);
		HttpEntity entity = new StringEntity(json, "utf-8");
		post.setEntity(entity);
		try(CloseableHttpResponse response = httpClient.execute(post)){
			if(response.getStatusLine().getStatusCode() != 200) {
				String ret = EntityUtils.toString(response.getEntity());
				model.setMessageState(ret);
			}
			EntityUtils.consume(response.getEntity());
			return model;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
