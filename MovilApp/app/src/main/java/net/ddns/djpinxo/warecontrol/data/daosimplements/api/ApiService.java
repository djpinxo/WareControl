package net.ddns.djpinxo.warecontrol.data.daosimplements.api;

import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("usuarios")
    Call <List<User>> getUsers();
    @GET("usuarios/{userEmail}")
    Call <User> getUser(@Path("userEmail") String userEmail);

}
