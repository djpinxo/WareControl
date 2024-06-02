package net.ddns.djpinxo.warecontrol.data.daosimplements.api;

import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("usuarios")
    Call <List<User>> getUsers();
    @GET("usuarios/{userEmail}")
    Call <User> getUser(@Path("userEmail") String userEmail);
    @POST("usuarios")
    Call <User> insertUser(@Body User user);
    @PUT("usuarios/{userEmail}")
    Call <User> updateUser(@Path("userEmail") String userEmail, @Body User user);
    @DELETE("usuarios/{userEmail}")
    Call <Void> deleteUser(@Path("userEmail") String userEmail);

}
