package net.ddns.djpinxo.warecontrol.data.daosimplements.api;


import android.util.Log;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDaoApiImplement implements UserDao {


    private User user = null;


    @Override
    public void getUsers(FragmentCallback<List<User>> fragmentCallback) {
        ApiService apiService = ApiClient.getClient("", MainActivity.userLogin).create(ApiService.class);
        Call <List<User>> call = apiService.getUsers();


        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void getUser(FragmentCallback<User> fragmentCallback, String email) {
        ApiService apiService = ApiClient.getClient("", MainActivity.userLogin).create(ApiService.class);
        Call <User> call = apiService.getUser(email);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void insertUser(FragmentCallback<User> fragmentCallback, User user) {
        ApiService apiService = ApiClient.getClient("", MainActivity.userLogin).create(ApiService.class);
        Call <User> call = apiService.insertUser(user);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void updateUser(FragmentCallback<User> fragmentCallback, User user) {
        ApiService apiService = ApiClient.getClient("", MainActivity.userLogin).create(ApiService.class);
        Call <User> call = apiService.updateUser(user.getEmail(), user);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fragmentCallback.callbackDataAcessSuccess(response.body());
                }
                else{
                    fragmentCallback.callbackDataAcessError(null);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w(this.getClass().getName(), t.getMessage());
                fragmentCallback.callbackDataAcessError(null);
            }
        });
    }

    @Override
    public void deleteUser(FragmentCallback<Boolean> fragmentCallback, String email) {
        ApiService apiService = ApiClient.getClient("", MainActivity.userLogin).create(ApiService.class);
        Call <Void> call = apiService.deleteUser(email);


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

    /*
    public List<User> getUsers() {
        return null;
    }
    public User getUser(String email){
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <User> call = apiService.getUser(email);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        return user;
    }

    public void getUser(FragmentCallback fragment, String email) {
        this.fragment = fragment;
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <User> call = apiService.getUser(email);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                fragment.callbackDataAcessSuccess(user);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public User insertUser(User user) {
        return null;
    }

    public User updateUser(User user) {
        return null;
    }

    public boolean deleteUser(String email) {
        return false;
    }*/
}
