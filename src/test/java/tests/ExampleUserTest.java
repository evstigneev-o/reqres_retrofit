package tests;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ExampleUserTest extends BaseTest{

    @Test
    public void getUsersListShouldGetCurrentPage() throws IOException {
        int pageNumber = 2;
        Response<ListUsersResponseModel> response = userService.getListUsers(pageNumber).execute();
        assertAll(
                () -> assertThat(response.code()).isEqualTo(200),
                () -> assertThat(response.body().getPage()).isEqualTo(pageNumber)
        );
    }

    @Test
    public void getSingleUserShouldGetCurrentUser() throws IOException {
        int userId = 6;
        Response<UserResponseModel> response = userService.getSingleUser(userId).execute();
        assertAll(
                () -> assertThat(response.code()).isEqualTo(200),
                () -> assertThat(response.body().getData().getId()).isEqualTo(userId)
        );
    }

    @Test
    public void createUserShouldCreateNewUser() throws IOException {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name("morpheus")
                .job("leader")
                .build();
        Response<CreateUserResponseModel> response = userService.createUser(requestBody).execute();
        assertAll(
                () -> assertThat(response.code()).isEqualTo(201),
                () -> assertThat(response.body().getName()).isEqualTo(requestBody.getName()),
                () -> assertThat(response.body().getJob()).isEqualTo(requestBody.getJob())
        );

    }

    @Test
    public void updateUserShouldUpdateUserJob() throws IOException {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name("morpheus")
                .job("leader")
                .build();
        String userId = userService.createUser(requestBody).execute().body().getId();
        requestBody.setJob("Bomj");

        Response<UpdateUserResponseModel> response = userService.updateUser(Integer.parseInt(userId),requestBody).execute();
        assertAll(
                () -> assertThat(response.code()).isEqualTo(200),
                () -> assertThat(response.body().getName()).isEqualTo(requestBody.getName()),
                () -> assertThat(response.body().getJob()).isEqualTo(requestBody.getJob())
        );
    }

    @Test
    public void deleteUserShouldHasCode204() throws IOException {
        UserRequestModel requestBody = UserRequestModel.builder()
                .name("morpheus")
                .job("leader")
                .build();
        String userId = userService.createUser(requestBody).execute().body().getId();

        Response<Void> response = userService.deleteUser(Integer.valueOf(userId)).execute();
        assertThat(response.code()).isEqualTo(204);
    }
}
