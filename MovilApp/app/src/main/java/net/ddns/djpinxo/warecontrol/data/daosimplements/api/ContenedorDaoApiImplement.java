package net.ddns.djpinxo.warecontrol.data.daosimplements.api;


import android.util.Log;

import net.ddns.djpinxo.warecontrol.data.daos.ContenedorDao;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContenedorDaoApiImplement implements ContenedorDao {


    private Contenedor contenedor = null;


    @Override
    public void getContenedores(FragmentCallback<List<Contenedor>> fragmentCallback) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <List<Contenedor>> call = apiService.getContenedores();


        call.enqueue(new Callback<List<Contenedor>>() {
            @Override
            public void onResponse(Call<List<Contenedor>> call, Response<List<Contenedor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<List<Contenedor>> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void getContenedor(FragmentCallback<Contenedor> fragmentCallback, long id) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Contenedor> call = apiService.getContenedor(id);


        call.enqueue(new Callback<Contenedor>() {
            @Override
            public void onResponse(Call<Contenedor> call, Response<Contenedor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Contenedor> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void insertContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Contenedor> call = apiService.insertContenedor(contenedor);


        call.enqueue(new Callback<Contenedor>() {
            @Override
            public void onResponse(Call<Contenedor> call, Response<Contenedor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Contenedor> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void updateContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Contenedor> call = apiService.updateContenedor(contenedor.getId(), contenedor);


        call.enqueue(new Callback<Contenedor>() {
            @Override
            public void onResponse(Call<Contenedor> call, Response<Contenedor> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Contenedor> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void deleteContenedor(FragmentCallback<Boolean> fragmentCallback, long id) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Void> call = apiService.deleteContenedor(id);


        call.enqueue(new Callback <Void>() {
            @Override
            public void onResponse(Call <Void> call, Response <Void> response) {
                if (response.isSuccessful()) {
                    fragmentCallback.callbackDataAcessSuccess(true);
                }
                else{
                    fragmentCallback.callbackDataAcessError(false);
                }
            }

            @Override
            public void onFailure(Call <Void> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(false);
            }
        });
    }
    
}
