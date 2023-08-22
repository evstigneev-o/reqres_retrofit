package tests;

import in.reqres.api.UserService;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import net.datafaker.Faker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Locale;

public class BaseTest {
    Faker faker = new Faker();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(new AllureOkHttp3())
            .build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(client)
            .build();
    UserService userService = retrofit.create(UserService.class);
}
