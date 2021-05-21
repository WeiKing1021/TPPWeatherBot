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

		System.out.println("A");

		JsonNode location_node = luisResult.getEntities().get("Location");
		JsonNode datetime_node = luisResult.getEntities().get("datetime");
		
		
		
		String location_name = null;
		String datetime_text = null;
		System.out.println("B");
		if(location_node.hasNonNull(0)) {
			System.out.println("11");
			location_name = location_node.get(0).get(0).asText();
			System.out.println("12");
		}
		
		if(datetime_node.hasNonNull(0)) {
			System.out.println("21");
			datetime_text = datetime_node.get(0).get("timex").get(0).asText();
			System.out.println("22");
		}
		
		
		
		System.out.println("C");
		
		

		System.out.println(location_name);
		System.out.println(datetime_text);
		
		String respone_message = null;

		System.out.println("D");

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
}
