package com.dipawp.chatgpt.api;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChoiceAdapter implements JsonDeserializer<ChatCompletionChoice>{

	@Override
	public ChatCompletionChoice deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
        ChatCompletionMessage message = context.deserialize(jsonObject.get("message"), ChatCompletionMessage.class);
        JsonElement selectedItemElement = jsonObject.get("message");
        if (selectedItemElement != null && !selectedItemElement.isJsonNull()) {
            ChatCompletionChoice choice = new ChatCompletionChoice();
            choice.setMessage(message);
            return choice;
        } else {
            return null;
        }
	}

}
