package net.ddns.djpinxo.warecontrol.data.daosimplements.api;


import android.util.Log;

import net.ddns.djpinxo.warecontrol.data.daos.ItemDao;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDaoApiImplement implements ItemDao {


    private Item item = null;


    @Override
    public void getItems(FragmentCallback<List<Item>> fragmentCallback) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <List<Item>> call = apiService.getItems();


        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void getItem(FragmentCallback<Item> fragmentCallback, long id) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Item> call = apiService.getItem(id);


        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void insertItem(FragmentCallback<Item> fragmentCallback, Item item) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Item> call = apiService.insertItem(item);


        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void updateItem(FragmentCallback<Item> fragmentCallback, Item item) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Item> call = apiService.updateItem(item.getId(), item);


        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void deleteItem(FragmentCallback<Boolean> fragmentCallback, long id) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <Void> call = apiService.deleteItem(id);


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
