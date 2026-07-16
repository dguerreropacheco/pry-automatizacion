package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutUser implements Task {

    private final String username;
    private final String password;
    private final String phone;

    public PutUser(String username, String password, String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public static Performable withData(String username, String password, String phone) {
        return instrumented(PutUser.class, username, password, phone);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        //Put - con Path
        actor.attemptsTo(
                Put.to("/{username}")
                        .with(requestSpecification -> requestSpecification
                                .pathParam("username", username)
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .body(
                                    """
                                        {
                                          "username": "%s",
                                          "password": "%s",
                                          "phone": "%s"
                                        }
                                    """
                                .formatted(username, password, phone))
                                .log().all()
                        )
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}