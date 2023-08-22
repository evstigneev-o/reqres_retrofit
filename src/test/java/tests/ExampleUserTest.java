package tests;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ExampleUserTest extends BaseTest {
    @Test
    public void getUsersListShouldGetCurrentPage() {
        int pageNumber = 2;
        Response<ListUsersResponseModel> response = step("Get usersList", () -> userService.getListUsers(pageNumber).execute());
        step("Validate response", () ->
                assertAll(
                        () -> assertThat(response.code()).withFailMessage("Invalid status code").isEqualTo(200),
                        () -> assertThat(response.body().getPage()).withFailMessage("Incorrect page number").isEqualTo(pageNumber)
                )
        );
    }

    @Test
    public void getSingleUserShouldGetCurrentUser() {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name(faker.name().firstName())
                .job(faker.job().position())
                .build();
        String userId = step("Create new user", () -> userService.createUser(requestBody).execute().body().getId());
        Response<UserResponseModel> response = step("Get user by id", () -> userService.getSingleUser(Integer.parseInt(userId)).execute());
        step("Validate response", () ->
                assertAll(
                        () -> assertThat(response.code()).withFailMessage("Invalid status code").isEqualTo(200),
                        () -> assertThat(response.body().getData().getId()).withFailMessage("Incorrect user Id").isEqualTo(userId)
                )
        );
    }


    @Test
    public void createUserShouldCreateNewUser() {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name(faker.name().firstName())
                .job(faker.job().position())
                .build();
        Response<CreateUserResponseModel> response = step("Create new user", () -> userService.createUser(requestBody).execute());
        step("Validate response", () ->
                assertAll(
                        () -> assertThat(response.code()).withFailMessage("Invalid status code").isEqualTo(201),
                        () -> assertThat(response.body().getName()).withFailMessage("Invalid user name").isEqualTo(requestBody.getName()),
                        () -> assertThat(response.body().getJob()).withFailMessage("Invalid user job").isEqualTo(requestBody.getJob())
                )
        );
    }

    @Test
    public void updateUserShouldUpdateUserJob() {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name(faker.name().firstName())
                .job(faker.job().position())
                .build();
        String userId = step("Create new user", () -> userService.createUser(requestBody).execute().body().getId());
        requestBody.setJob("Bomj");

        Response<UpdateUserResponseModel> response = step("Update user job", () -> userService.updateUser(Integer.parseInt(userId), requestBody).execute());
        step("Validate response", () ->
                assertAll(
                        () -> assertThat(response.code()).isEqualTo(200),
                        () -> assertThat(response.body().getName()).isEqualTo(requestBody.getName()),
                        () -> assertThat(response.body().getJob()).isEqualTo(requestBody.getJob())
                )
        );
    }

    @Test
    public void deleteUserShouldHasCode204() {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name(faker.name().firstName())
                .job(faker.job().position())
                .build();
        String userId = step("Create new user", () -> userService.createUser(requestBody).execute().body().getId());
        Response<Void> response = step("Delete user", () -> userService.deleteUser(Integer.parseInt(userId)).execute());
        step("Validate response status code", () -> assertThat(response.code()).isEqualTo(204));
    }
}
