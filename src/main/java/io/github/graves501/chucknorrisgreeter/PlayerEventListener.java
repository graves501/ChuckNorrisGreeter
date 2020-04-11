package io.github.graves501.chucknorrisgreeter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Log
public class PlayerEventListener implements Listener {

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) throws Exception {
        Bukkit.broadcastMessage(getJoke());
    }

    private String getJoke() throws Exception {

        final String jokeResponse = requestJokeAndParseResponse();
        return formatJokeResponse(jokeResponse);
    }

    private String requestJokeAndParseResponse() throws IOException {

        final String curlRequest = "curl -s https://api.chucknorris.io/jokes/random";
        ProcessBuilder processBuilder = new ProcessBuilder(curlRequest.split(" "));
        final Process process = processBuilder.start();

        JsonElement jsonRootElement = new JsonParser()
            .parse(new InputStreamReader(process.getInputStream()));
        JsonObject jsonObject = jsonRootElement.getAsJsonObject();

        final String receivedJoke = jsonObject.get("value").getAsString();
        log.info("Received joke: " + receivedJoke);

        return receivedJoke;
    }

    private String formatJokeResponse(final String joke) {
        final String JOKE_PREFIX = ChatColor.RED + "[Chuck Norris Joke] ";
        return JOKE_PREFIX + ChatColor.WHITE + joke;
    }
}
