package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetUserByUsername implements Task {

    private final String username;

    public GetUserByUsername(String username) {
        this.username = username;
    }

    public static Performable withData(String username) {
        return instrumented(GetUserByUsername.class, username);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        //GET - con Path
        actor.attemptsTo(
                Get.resource("/{username}")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .pathParam("username", username)
                                .log().all()
                        )
        );

        SerenityRest.lastResponse().body().prettyPrint();
    }
}