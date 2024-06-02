package net.ddns.djpinxo.warecontrol.data.daosimplements.api;


import androidx.fragment.app.Fragment;

import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDaoApiImplement implements UserDao {
    public List<User> getUsers() {
        return null;
    }

    private User user = null;

    public User getUser(String email){
        return null;
    }
    public User getUser(String email, Fragment fragment) {
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

    public User insertUser(User user) {
        return null;
    }

    public User updateUser(User user) {
        return null;
    }

    public boolean deleteUser(String email) {
        return false;
    }
}
