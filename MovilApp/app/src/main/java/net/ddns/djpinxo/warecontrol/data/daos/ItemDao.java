package net.ddns.djpinxo.warecontrol.data.daos;

import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

public interface ItemDao {

    public void getItems(FragmentCallback<List<Item>> fragmentCallback);
    public void getItem(FragmentCallback<Item> fragmentCallback, long id);
    public void insertItem(FragmentCallback<Item> fragmentCallback, Item item);
    public void updateItem(FragmentCallback<Item> fragmentCallback, Item item);
    public void deleteItem(FragmentCallback<Boolean> fragmentCallback, long id);
}
