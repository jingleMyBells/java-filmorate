package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    private URI getUrl() {
        return URI.create("http://localhost:8080/users");
    }

    private HttpRequest buildPostRequest(URI url, String json) {
        return HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .header("Content-Type", "application/json")
                .build();
    }


    @Test
    @DisplayName("Проверяет невозможность добавления юзера с плохой почтой")
    void shouldReturn400InvalidUserEmailTest() {
        User user = new User(
                0,
                "tomhanks.ru",
                "tomhanks",
                "Tom Hanks",
                LocalDate.of(1956, 7, 9)
        );

        URI url = getUrl();
        Gson gson = new Gson();
        HttpRequest request = buildPostRequest(url, gson.toJson(user));
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(400, response.statusCode());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверяет невозможность добавления юзера с плохим логином")
    void shouldReturn400InvalidUserLoginTest() {
        User user = new User(
                0,
                "tomhanks@yandex.ru",
                "tom hanks",
                "Tom Hanks",
                LocalDate.of(1956, 7, 9)
        );

        URI url = getUrl();
        Gson gson = new Gson();
        HttpRequest request = buildPostRequest(url, gson.toJson(user));
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(400, response.statusCode());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Проверяет невозможность добавления юзера с плохой датой рождения")
    void shouldReturn400InvalidUserBirthdayTest() {
        User user = new User(
                0,
                "tomhanks@yandex.ru",
                "tomhanks",
                "Tom Hanks",
                LocalDate.of(2120, 7, 9)
        );

        URI url = getUrl();
        Gson gson = new Gson();
        HttpRequest request = buildPostRequest(url, gson.toJson(user));
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Assertions.assertEquals(400, response.statusCode());

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
