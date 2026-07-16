package org.example.Tasks;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetLoginUser implements Task {

    //Asignamos unos valores de prueba para simular el logeo
    private final String username = "admintest";
    private final String password = "admintest";

    public static Performable login() {
        return instrumented(GetLoginUser.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        //GET - con Query
        actor.attemptsTo(Get.resource("/login")
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Accept", "application/json")
                                .queryParam("username", username)
                                .queryParam("password", password)
                                .log().all()
                        )
        );
        //Solo imprimimos la respuesta de que inició sesión
        SerenityRest.lastResponse().body().prettyPrint();
    }
}