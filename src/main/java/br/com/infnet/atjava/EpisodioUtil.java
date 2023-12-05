package br.com.infnet.atjava;

import br.com.infnet.atjava.model.Episodio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EpisodioUtil {
    public Episodio buscarEpisodioPorId(int id) {
        Logger logger = LoggerFactory.getLogger(EpisodioUtil.class);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("https://rickandmortyapi.com/api/episode/" + id))
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info(String.valueOf(response.statusCode()));

            if(response.statusCode() == 404){
                throw new RuntimeException(response.body());
            }
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response.body(), Episodio.class);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
