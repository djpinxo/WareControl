package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.io.IOException;

import okhttp3.ResponseBody;

public class SelectItemImagenFragment implements FragmentCallback<ResponseBody> {

    //creado por mantener una estructura aun que no sea fragment

    private Activity activity;
    private ImageView imageView;

    private Item itemModel;

    public SelectItemImagenFragment(){
        super();
    }
    public SelectItemImagenFragment(Item itemModel, Activity activity, ImageView imageView){
        this();
        this.itemModel=itemModel;
        this.activity = activity;
        this.imageView = imageView;

    }

    public void getItemImagen(){
        if(validateItemForm()){
            MainActivity.itemDao.getItemImagen(this, itemModel.getId());
        }
        else {
            Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm() {
        return !itemModel.getId().toString().isEmpty();
    }

    @Override
    public void callbackDataAcessSuccess(ResponseBody responseBody) {
        //ImageButton imageButton = activity.findViewById(R.id.imageView);
        try {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(responseBody.bytes(), 0, (int)responseBody.contentLength()));
        } catch (IOException e) {
            callbackDataAcessError(responseBody);
        }
    }

    @Override
    public void callbackDataAcessError(ResponseBody responseBody) {
        //Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        imageView.setImageResource(R.drawable.camara);
    }
}