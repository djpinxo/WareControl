package net.ddns.djpinxo.warecontrol.data.daosimplements.api;


import android.util.Log;

import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDaoApiImplement {


    private User user = null;





    public void loginUser(FragmentCallback<User> fragmentCallback, User user) {
        ApiService apiService = ApiClient.getClient("").create(ApiService.class);
        Call <User> call = apiService.loginUser(user);


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

}
