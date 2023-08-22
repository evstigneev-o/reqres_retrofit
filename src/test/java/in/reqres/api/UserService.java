package in.reqres.api;

import in.reqres.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {
    @GET("/api/users/{userId}")
    Call<UserResponseModel> getSingleUser(@Path("userId") int userId);

    @GET("api/users")
    Call<ListUsersResponseModel> getListUsers(@Query("page") int pageNumber);

    @POST("api/users")
    Call<CreateUserResponseModel> createUser(@Body UserRequestModel userRequestModel);

    @PUT("api/users/{id}")
    Call<UpdateUserResponseModel> updateUser(@Path("id") int id, @Body UserRequestModel userRequestModel);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
