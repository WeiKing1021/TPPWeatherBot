package com.microsoft.bot.sample.core;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.RecognizerResult;
import com.microsoft.bot.dialogs.DialogTurnResult;
import com.microsoft.bot.dialogs.WaterfallStepContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.InputHints;

public class GetWeather {
	
	public static CompletableFuture<DialogTurnResult> get(RecognizerResult luisResult, WaterfallStepContext stepContext) {
		
		String location_name = getLocation(luisResult);
		String datetime_text = getDatetimeText(luisResult);
		
		System.out.println(location_name);
		System.out.println(datetime_text);
		
		String respone_message = null;
		
		if(location_name != null && datetime_text != null){
			
			respone_message = "The weather in " + location_name + " on " + datetime_text + " is rainy.";
			System.out.println("E1");
		}
		else if (datetime_text != null){
			
			System.out.println("E2");
			respone_message = "Please type the location to search the weather.";
		}
		else if (location_name != null){
			
			System.out.println("E3");
			respone_message = "The weather in " + location_name + " is sunny today.";
		}
		
		System.out.println("F");
				
        Activity msg = MessageFactory
        .text(
        		respone_message, respone_message,
            InputHints.IGNORING_INPUT
        );
        
		return stepContext.getContext().sendActivities(msg).thenCompose(resourceResponse -> stepContext.next(null));
	}
	
	private static String getLocation(RecognizerResult luisResult) {
		
		JsonNode entities = luisResult.getEntities();
		
		if (!entities.has("Location")) {
			
			return null;
		}
		
		JsonNode location_node = entities.get("Location");
		
		if (!location_node.has(0)) {
			
			return null;
		}
		
		if (!location_node.get(0).has(0)) {
			
			return null;
		}
		
		return location_node.get(0).get(0).asText();
	}
	
	private static String getDatetimeText(RecognizerResult luisResult) {
		
		JsonNode entities = luisResult.getEntities();
		
		if (!entities.has("datetime")) {
			
			return null;
		}
		
		JsonNode datetime_node = entities.get("datetime");
		
		if (!datetime_node.has(0)) {
			
			return null;
		}
		
		if (!datetime_node.get(0).has("timex")) {
			
			return null;
		}
		
		if (datetime_node.get(0).get("timex").has(0)) {
			
			return null;
		}
		
		return datetime_node.get(0).get("timex").get(0).asText();
	}
}
