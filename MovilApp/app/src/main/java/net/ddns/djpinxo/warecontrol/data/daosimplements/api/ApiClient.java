package net.ddns.djpinxo.warecontrol.data.daosimplements.api;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.io.IOException;
import java.util.Base64;

public class ApiClient {
    private static String BASE_URL = "http://djpinxo.ddns.net:81/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url, User user) {


        if (retrofit == null && user!=null) {
            BASE_URL=url;
            //para cabecera se seguridad
            String usuario="";
            String password="";
            if(user!=null){
                usuario=user.getEmail();
                password=user.getPassword();
            }


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(usuario, password))
                    .build();
            okHttpClient.interceptors().get(1);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //fin
            //para llamadas sin seguridad
            /*retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/
        }
        else if (retrofit == null && user==null) {
            BASE_URL=url;
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        else if (retrofit != null && user==null) {
            retrofit = retrofit.newBuilder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        else if (retrofit != null && user!=null) {
            String usuario="";
            String password="";
            if(user!=null){
                usuario=user.getEmail();
                password=user.getPassword();
            }

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(usuario, password))
                    .build();

            retrofit = retrofit.newBuilder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    /*public static Retrofit getClient(Context context, User user) {
        return getClient(context.getString(R.string.ApiURL), user);
    }*/
    /*public static Retrofit getClient(String url, User user) {

        return getClient(url, user);
    }*/


}
class BasicAuthInterceptor implements Interceptor {
    private String credentials;

    public BasicAuthInterceptor(String usuario, String password) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.credentials = "Basic " + Base64.getEncoder().encodeToString((usuario + ":" + password).getBytes());
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", credentials);

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
