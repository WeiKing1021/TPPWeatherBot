package com.microsoft.bot.sample.core;

import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.RecognizerResult;
import com.microsoft.bot.dialogs.DialogTurnResult;
import com.microsoft.bot.dialogs.WaterfallStepContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.InputHints;

public class AskWeather {
	
	public static CompletableFuture<DialogTurnResult> get(RecognizerResult luisResult, WaterfallStepContext stepContext) {
		
		JsonNode node = luisResult.getEntities().get("Location");
		JsonNode time_node = luisResult.getEntities().get("datetime");
		JsonNode state_node = luisResult.getEntities().get("WeatherState");

		String location_name = node.get(0).get(0).asText();
		String datetime_text = time_node.get(0).get("timex").get(0).asText();
		String weatherState = state_node.get(0).get(0).asText();

		System.out.println(state_node);
		System.out.println(weatherState);
		
		String respone_message = "你是不是想知道關於 " + location_name + " 的位置資訊?";
		
        Activity msg = MessageFactory
        .text(
        		respone_message, respone_message,
            InputHints.IGNORING_INPUT
        );
        
		return stepContext.getContext().sendActivities(msg).thenCompose(resourceResponse -> stepContext.next(null));
	}
}
