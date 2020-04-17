package io.github.graves501.chucknorrisgreeter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Slf4j
public class PlayerEventListener implements Listener {

    private static final String CHUCK_NORRIS_API_CURL_REQUEST
        = "curl -s https://api.chucknorris.io/jokes/random";
    private static final String WHITE_SPACE_SEPARATOR = " ";
    private static final String VALUE = "value";
    private static final String JOKE_PREFIX = ChatColor.RED + "[Chuck Norris Joke] ";

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) throws Exception {
        Bukkit.broadcastMessage(getJoke());
    }

    private String getJoke() throws Exception {

        final String jokeResponse = requestJokeAndParseResponse();
        return formatJokeResponse(jokeResponse);
    }

    private String requestJokeAndParseResponse() throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder(CHUCK_NORRIS_API_CURL_REQUEST.split(
            WHITE_SPACE_SEPARATOR));
        final Process process = processBuilder.start();

        JsonElement jsonRootElement = new JsonParser()
            .parse(new InputStreamReader(process.getInputStream()));
        JsonObject jsonObject = jsonRootElement.getAsJsonObject();

        final String receivedJoke = jsonObject.get(VALUE).getAsString();

        log.info("Received joke: {}", receivedJoke);

        return receivedJoke;
    }

    private String formatJokeResponse(final String joke) {
        return JOKE_PREFIX + ChatColor.WHITE + joke;
    }
}
