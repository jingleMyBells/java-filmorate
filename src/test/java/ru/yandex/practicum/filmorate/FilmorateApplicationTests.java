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
class FilmorateApplicationTests {

	private URI getUrl(String path) {
		return URI.create("http://localhost:8080" + path);
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
	@DisplayName("Проверяет невозможность добавления фильма с плохим именем")
	void shouldReturn400InvalidFilmNameTest() {
		Film film = new Film(
				0,
				null,
				"At a Louisiana assisted-living home in 1999, " +
						"retiree Paul Edgecomb begins to cry while watching " +
						"the film Top Hat. His companion Elaine becomes concerned, " +
						"and Paul explains to her that the film reminded him of events " +
						"that he witnessed when he was an officer at " +
						"Cold Mountain Penitentiary's death row, nicknamed \"The Green Mile\". ",
				LocalDate.of(1999, 12, 6),
				189
		);

		URI url = getUrl("/films");
		Gson gson = new Gson();
		HttpRequest request = buildPostRequest(url, gson.toJson(film));
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Assertions.assertEquals(400, response.statusCode());

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("Проверяет невозможность добавления фильма с плохим описанием")
	void shouldReturn400InvalidFilmDescrTest() {
		Film film = new Film(
				0,
				"The Green Mile",
				"At a Louisiana assisted-living home in 1999, " +
						"retiree Paul Edgecomb begins to cry while watching " +
						"the film Top Hat. His companion Elaine becomes concerned, " +
						"and Paul explains to her that the film reminded him of events " +
						"that he witnessed when he was an officer at " +
						"Cold Mountain Penitentiary's death row, nicknamed \"The Green Mile\". ".repeat(10),
				LocalDate.of(1999, 12, 6),
				189
		);

		URI url = getUrl("/films");
		Gson gson = new Gson();
		HttpRequest request = buildPostRequest(url, gson.toJson(film));
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Assertions.assertEquals(400, response.statusCode());

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("Проверяет невозможность добавления фильма с плохой датой релиза")
	void shouldReturn400InvalidFilmReleaseDateTest() {
		Film film = new Film(
				0,
				"The Green Mile",
				"At a Louisiana assisted-living home in 1999, " +
						"retiree Paul Edgecomb begins to cry while watching " +
						"the film Top Hat. His companion Elaine becomes concerned, " +
						"and Paul explains to her that the film reminded him of events " +
						"that he witnessed when he was an officer at " +
						"Cold Mountain Penitentiary's death row, nicknamed \"The Green Mile\". ",
				LocalDate.of(1799, 12, 6),
				189
		);

		URI url = getUrl("/films");
		Gson gson = new Gson();
		HttpRequest request = buildPostRequest(url, gson.toJson(film));
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Assertions.assertEquals(400, response.statusCode());

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	@DisplayName("Проверяет невозможность добавления фильма с плохой длительностью")
	void shouldReturn400InvalidFilmDurationTest() {
		Film film = new Film(
				0,
				"The Green Mile",
				"At a Louisiana assisted-living home in 1999, " +
						"retiree Paul Edgecomb begins to cry while watching " +
						"the film Top Hat. His companion Elaine becomes concerned, " +
						"and Paul explains to her that the film reminded him of events " +
						"that he witnessed when he was an officer at " +
						"Cold Mountain Penitentiary's death row, nicknamed \"The Green Mile\". ",
				LocalDate.of(1999, 12, 6),
				-1
		);

		URI url = getUrl("/films");
		Gson gson = new Gson();
		HttpRequest request = buildPostRequest(url, gson.toJson(film));
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Assertions.assertEquals(400, response.statusCode());

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
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

		URI url = getUrl("/users");
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

		URI url = getUrl("/users");
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

		URI url = getUrl("/users");
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
