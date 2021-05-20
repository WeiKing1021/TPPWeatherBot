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

		JsonNode location_node = luisResult.getEntities().get("Location");
		JsonNode datetime_node = luisResult.getEntities().get("datetime");
		
		String location_name = location_node.get(0).get(0).asText();
		String datetime_text = datetime_node.get(0).get("timex").get(0).asText();
		
		String respone_message = "你是不是想知道關於 " + location_name + " 在 " + datetime_text + " 的天氣狀況呢?";
		
        Activity msg = MessageFactory
        .text(
        		respone_message, respone_message,
            InputHints.IGNORING_INPUT
        );
        
		return stepContext.getContext().sendActivities(msg).thenCompose(resourceResponse -> stepContext.next(null));
	}
}
