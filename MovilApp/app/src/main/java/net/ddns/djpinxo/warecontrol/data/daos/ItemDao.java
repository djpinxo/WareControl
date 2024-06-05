package net.ddns.djpinxo.warecontrol.data.daos;

import android.widget.ImageView;

import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public interface ItemDao {

    public void getItems(FragmentCallback<List<Item>> fragmentCallback);
    public void getItems(FragmentCallback<List<Item>> fragmentCallback, String query);
    public void getItem(FragmentCallback<Item> fragmentCallback, long id);
    public void insertItem(FragmentCallback<Item> fragmentCallback, Item item);
    public void updateItem(FragmentCallback<Item> fragmentCallback, Item item);
    public void deleteItem(FragmentCallback<Boolean> fragmentCallback, long id);
    public void getItemImagen(FragmentCallback<ResponseBody> fragmentCallback, long id);
    public void insertItemImagen(FragmentCallback<String> fragmentCallback, long id, ImageView imageView);
}
