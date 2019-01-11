package be.wouterversyck.slackintegration.web.controllers.restControllers;

import be.wouterversyck.slackintegration.model.funFact.FunFact;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.time.DateUtils.isSameDay;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@TestPropertySource(value={"classpath:application-test.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FunFactControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void postFunfactShouldReturnSameFunfact() {
        FunFact funFact = FunFact.builder()
                .withAuthor("test")
                .withFunfact("fact")
                .withTitle("title").build();
        FunFact response = givenWithAuth()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(funFact)
            .when()
                .post("/funfact")
                .as(FunFact.class);

        assertThat(response.getAuthor(), equalTo(funFact.getAuthor()));
        assertTrue(isSameDay(response.getCreateDate(), new Date()));
        assertThat(response.getId(), not(isEmptyString()));
        assertThat(response.getVoteCount(), equalTo(0));
        assertThat(response.getVotes(), is(not(empty())));
        assertThat(response.getTitle(), equalTo(funFact.getTitle()));
        assertThat(response.getFunFact(), equalTo(funFact.getFunFact()));
    }

    @Test
    public void postFunfactShouldReturnErrorWhenFactNotProvided() {
        FunFact funFact = FunFact.builder()
                .withAuthor("test")
                .withTitle("title").build();
        givenWithAuth()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(funFact)
                .when()
                .post("/funfact")
                .then()
                .statusCode(400)
                .body("status", is("400"))
                .body("path", is("/funfact/"))
                .body("error", is("Bad Request"))
                .body("message", containsString("fun_fact"))
                .body("message", containsString("empty"));
    }

    @Test
    public void funFactEndpointIsSecured() {
        given()
                .get("/funfact")
                .then()
                .statusCode(401);
    }

    private RequestSpecification givenWithAuth() {
        return given().auth().basic("user", "password");
    }
}