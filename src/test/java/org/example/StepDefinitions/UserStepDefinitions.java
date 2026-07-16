package org.example.StepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.example.Questions.ResponseCode;
import org.example.Tasks.*;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserStepDefinitions {

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de user")
    public void elActorEstableceElEndpointDeUser(Actor actor) {
        actor.whoCan(CallAnApi.at("https://petstore.swagger.io/v2/user"));
    }

    //Este step forma parte de una precondición que pide que el usuario esté logeado para realizar las operaciones de create, update y delete
    //Aunque no es restrictivo, se ha implementado por el flujo de acuerdo a la documentación
    @And("el {actor} inicia sesión con un usuario y contraseña válidos")
    public void elActorIniciaSesiónConUnUsuarioYContraseñaVálidos(Actor actor) {
        actor.attemptsTo(
                GetLoginUser.login()
        );
    }

    @When("el actor crea un user con {string} {string} {string} {string} {string} {string} {string} {string}")
    public void elActorCreaUnUserCon(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        theActorInTheSpotlight().attemptsTo(
                PostUser.fromPage(id, username, firstName, lastName, email, password, phone, userStatus)
        );
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(seeThat("Código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }

    @When("el {actor} realiza una solicitud GET con {string}")
    public void elActorRealizaUnaSolicitudGET(Actor actor, String username) {
        actor.attemptsTo(
                GetUserByUsername.withData(username)
        );

    }

    @When("el {actor} actualiza el usuario {string} con los datos {string} {string}")
    public void elActorActualizaElUsuarioConLosDatos(Actor actor, String username, String password, String phone) {
        actor.attemptsTo(
                PutUser.withData(username,password,phone)
        );
    }


    @When("el {actor} elimina el usuario {string}")
    public void elActorEliminaElUsuario(Actor actor, String username) {
        actor.attemptsTo(
                DeleteUser.withData(username)
        );
    }
}

