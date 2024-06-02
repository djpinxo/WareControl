package net.ddns.djpinxo.warecontrol.data.daosimplements.api;
import android.app.Activity;
import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import net.ddns.djpinxo.warecontrol.R;

public class ApiClient {
    private static String BASE_URL = "http://djpinxo.ddns.net:81/";
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getClient(Context context) {
        return getClient(context.getString(R.string.ApiURL));
    }
    public static Retrofit getClient(String url) {
        BASE_URL=url;
        return getClient();
    }
}
