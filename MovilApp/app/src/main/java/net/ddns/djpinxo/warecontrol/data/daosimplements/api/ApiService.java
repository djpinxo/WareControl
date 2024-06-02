package net.ddns.djpinxo.warecontrol.data.daosimplements.api;

import android.graphics.Bitmap;
import android.media.Image;

import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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



    @GET("items")
    Call <List<Item>> getItems();
    @GET("items/{id}")
    Call <Item> getItem(@Path("id") long id);
    @POST("items")
    Call <Item> insertItem(@Body Item item);
    @PUT("items/{id}")
    Call <Item> updateItem(@Path("id") long id, @Body Item item);
    @DELETE("items/{id}")
    Call <Void> deleteItem(@Path("id") long id);

    //@Multipart
    @GET("items/{id}/imagen")
    Call <ResponseBody> getItemImagen(@Path("id") long id);
    @Multipart
    @POST("items/{id}/imagen")
    Call <Void> insertItemImagen(@Path("id") long id, @Part MultipartBody.Part imagen);


    @GET("contenedores")
    Call <List<Contenedor>> getContenedores();
    @GET("contenedores/{id}")
    Call <Contenedor> getContenedor(@Path("id") long id);
    @POST("contenedores")
    Call <Contenedor> insertContenedor(@Body Contenedor contenedor);
    @PUT("contenedores/{id}")
    Call <Contenedor> updateContenedor(@Path("id") long id, @Body Contenedor contenedor);
    @DELETE("contenedores/{id}")
    Call <Void> deleteContenedor(@Path("id") long id);
    @GET("contenedores/{id}/items")
    Call <List<Item>> getContenedorItems(@Path("id") long id);
    @GET("contenedores/{id}/contenedorhijos")
    Call <List<Contenedor>> getContenedorContenedorHijos(@Path("id") long id);

    @POST("login")
    Call <User> loginUser(@Body User user);
    @POST("register")
    Call <User> registerUser(@Body User user);

}
