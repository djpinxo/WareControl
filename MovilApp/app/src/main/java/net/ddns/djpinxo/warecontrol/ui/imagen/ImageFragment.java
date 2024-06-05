/*package net.ddns.djpinxo.warecontrol.ui.imagen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ApiClient;
import net.ddns.djpinxo.warecontrol.data.daosimplements.api.ApiService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ImageFragment extends Fragment {
    private ImageView imageView;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = view.findViewById(R.id.imageView);

        // Configura Retrofit
        Retrofit retrofit = ApiClient.getClient("",MainActivity.userLogin);
        apiService = retrofit.create(ApiService.class);

        // ID de la imagen que quieres recuperar
        Long imagenId = 1L;

        // Llama al endpoint para obtener la imagen
        Call<ResponseBody> call = apiService.getItemImagen(1L);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(response.body().bytes(), 0, (int)response.body().contentLength()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
*/