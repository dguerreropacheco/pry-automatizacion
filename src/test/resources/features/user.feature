@UserAll
Feature: APIS from User
  Como usuario
  Quiero gestionar User
  Para poder verificar las operaciones CRUD de User

  @CP01_CreateUser @User
  Scenario Outline: Crear un usuario exitosamente
    Given el actor establece el endpoint de user
    And el actor inicia sesión con un usuario y contraseña válidos
    When el actor crea un user con "<id>" "<username>" "<firstName>" "<lastName>" "<email>" "<password>" "<phone>" "<userStatus>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | id | username  |firstName | lastName | email              | password | phone     | userStatus |
      | 9  | dnuser    |Diana     | Guerrero | dianag@gmail.com   | 1e2o4s   | 987654321 | 0          |

  @CP02_GetUserByUsername @User
  Scenario Outline: Obtener un usuario por username exitosamente
    Given el actor establece el endpoint de user
    When el actor realiza una solicitud GET con "<username>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | username  |
      | diaprueba |

  @CP03_UpdateUser @User
  Scenario Outline: Actualizar un usuario exitosamente
    Given el actor establece el endpoint de user
    And el actor inicia sesión con un usuario y contraseña válidos
    And el actor crea un user con "10" "diaprueba" "dia" "diaz" "dd@gmail.com" "1230eo" "987883893" "0"
    When el actor actualiza el usuario "diaprueba" con los datos "<password>" "<phone>"
    Then el codigo de respuesta debe ser 200

    Examples:
      | password     | phone           |
      | nw1022ct     | 981828829       |

  @CP04_DeleteUser @User
  Scenario: Eliminar un usuario exitosamente
    Given el actor establece el endpoint de user
    And el actor inicia sesión con un usuario y contraseña válidos
    And el actor crea un user con "11" "nanuser" "nan" "perez" "nan@gmail.com" "pw45555" "987444493" "0"
    When el actor elimina el usuario "nanuser"
    Then el codigo de respuesta debe ser 200
