package com.dipawp.chatgpt.service;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.dipawp.chatgpt.api.ChatCompletionChoice;
import com.dipawp.chatgpt.api.ChatCompletionRequest;
import com.dipawp.chatgpt.api.ChoiceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


@Service
public class OAIService {
	
	private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

    private OkHttpClient client;
    private ExecutorService executorService;

    public OAIService() {
        client = new OkHttpClient();
        executorService = Executors.newCachedThreadPool();
    }

    private String executeRequest(Request request) throws IOException {
    	
    	Response response = null;
    	String result = "";
    	try {
    		response = client.newCall(request).execute();
	        result = response.body().string();
		} catch (Exception e) {

		}
		return result;
    	
    }

    public Future<String> completeChat(ChatCompletionRequest completionRequest) {
    	
    	Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChatCompletionChoice.class, new ChoiceAdapter())
                .create();
		String toJsonString = gson.toJson(completionRequest);
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, toJsonString);
        Request request = new Request.Builder()
        		.header("Authorization", "Bearer OPENAI_API_KEY")
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .build();

        Callable<String> callable = new Callable<String>() {
        	@Override
            public String call() throws Exception {
        		
                return executeRequest(request);
            }
        };
        return executorService.submit(callable);
    }
    public void shutdown() {
        executorService.shutdown();
    }

}
