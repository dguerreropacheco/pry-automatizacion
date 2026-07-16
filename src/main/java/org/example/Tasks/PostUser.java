package org.example.Tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostUser implements Task {

    private final String id, username, firstName, lastName, email, password, phone, userStatus;

    public PostUser(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userStatus = userStatus;
    }

    public static Performable fromPage(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        return instrumented(PostUser.class, id, username, firstName, lastName, email, password, phone, userStatus);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        //POST - con BODY
        actor.attemptsTo(Post.to("/")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .body(
                            """ 
                                 {
                                   "id": %s,
                                   "username": "%s",
                                   "firstName": "%s",
                                   "lastName": "%s",
                                   "email": "%s",
                                   "password": "%s",
                                   "phone": "%s",
                                   "userStatus": %s
                                 }
                            """
                        .formatted(id, username, firstName, lastName, email, password, phone, userStatus))
                        .log().all()));

        SerenityRest.lastResponse().body().prettyPrint();
    }
}
