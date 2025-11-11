package io.xstefank;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_5_MINI;

@Path("/hello")
public class GreetingResource {

    @Inject
    ChatModel chatModel;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        String answerA = chatModel.chat("Say Hello World");
        return "Hello from Quarkus REST " + answerA;
    }
}
