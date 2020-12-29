package io.github.graves501.chucknorrisgreeter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Slf4j
public class PlayerEventListener implements Listener {

    private static final String CHUCK_NORRIS_API = "https://api.chucknorris.io/jokes/random";
    private static final String CHUCK_NORRIS_API_CURL_REQUEST = "curl -s " + CHUCK_NORRIS_API;
    private static final String VALUE = "value";
    private static final String CHUCK_NORRIS_JOKE_PREFIX = ChatColor.RED + "[Chuck Norris Joke] ";

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent playerJoinEvent) throws IOException {
        Bukkit.broadcastMessage(getChuckNorrisJoke());
    }

    private String getChuckNorrisJoke() throws IOException {
        final String receivedChuckNorrisJoke = requestJokeAndParseResponse();
        return formatReceivedChuckJoke(receivedChuckNorrisJoke);
    }

    private String requestJokeAndParseResponse() throws IOException {
        ProcessBuilder jokeRequestProcessBuilder = new ProcessBuilder(
            CHUCK_NORRIS_API_CURL_REQUEST.split(StringUtils.SPACE));
        final Process jokeRequestProcess = jokeRequestProcessBuilder.start();

        JsonElement responseJsonRootElement = new JsonParser()
            .parse(new InputStreamReader(jokeRequestProcess.getInputStream()));
        JsonObject responseJsonObject = responseJsonRootElement.getAsJsonObject();

        final String receivedJoke = responseJsonObject.get(VALUE).getAsString();
        log.info("Received joke: {}", receivedJoke);

        return receivedJoke;
    }

    private String formatReceivedChuckJoke(final String chuckNorrisJoke) {
        return CHUCK_NORRIS_JOKE_PREFIX + ChatColor.WHITE + chuckNorrisJoke;
    }
}
