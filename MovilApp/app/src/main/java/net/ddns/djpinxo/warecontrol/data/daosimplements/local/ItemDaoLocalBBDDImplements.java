package net.ddns.djpinxo.warecontrol.data.daosimplements.local;

import net.ddns.djpinxo.warecontrol.data.daos.ItemDao;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ItemDaoLocalBBDDImplements implements ItemDao {

    private static List<Item> items;

    public ItemDaoLocalBBDDImplements(){
        super();
        if (items==null){
            items = new ArrayList<Item>();
            int numeroUsuarios=40;
            for(long l=0; l<numeroUsuarios; l++){
                items.add(new Item(l, "item"+l, "descripcion del contedor "+l, null));
            }
        }
    }


    public List<Item> getItemes() {
        return items;
    }

    public Item getItem(long id) {
        List <Item> itemsTemp = getItemes();
        Item result=null;
        for (Item item : itemsTemp){
            if(item.getId().equals(id)){
                result=item;
                break;
            }
        }

        return result;
    }

    public Item insertItem(Item itemTemp){
        Item result = getItem(itemTemp.getId());
        if (result == null){
            getItemes().add(itemTemp);
            return itemTemp;
        }
        else {
            return null;
        }
        //return result;
    }

    public Item updateItem(Item itemTemp){
        Item result = getItem(itemTemp.getId());
        if (result != null){
            result.setId(itemTemp.getId());
            result.setNombre(itemTemp.getNombre());
            result.setDescripcion(itemTemp.getDescripcion());
            result.setContenedor(itemTemp.getContenedor());
        }
        return result;
    }

    public boolean deleteItem(long id){
        boolean result=false;
        Item itemTemp = getItem(id);
        if (itemTemp != null){
            getItemes().remove(itemTemp);
            result=true;
        }
        return result;
    }


    @Override
    public void getItems(FragmentCallback<List<Item>> fragmentCallback) {
        fragmentCallback.callbackDataAcessSuccess(getItemes());
    }

    @Override
    public void getItem(FragmentCallback<Item> fragmentCallback, long id) {
        Item itemResult = getItem(id);
        if(itemResult != null)
            fragmentCallback.callbackDataAcessSuccess(itemResult);
        else
            fragmentCallback.callbackDataAcessError(itemResult);
    }

    @Override
    public void insertItem(FragmentCallback<Item> fragmentCallback, Item item) {
        Item itemResult = insertItem(item);
        if(itemResult != null)
            fragmentCallback.callbackDataAcessSuccess(itemResult);
        else
            fragmentCallback.callbackDataAcessError(itemResult);
    }

    @Override
    public void updateItem(FragmentCallback<Item> fragmentCallback, Item item) {
        Item itemResult = updateItem(item);
        if(itemResult != null)
            fragmentCallback.callbackDataAcessSuccess(itemResult);
        else
            fragmentCallback.callbackDataAcessError(itemResult);
    }

    @Override
    public void deleteItem(FragmentCallback<Boolean> fragmentCallback, long id) {
        if(deleteItem(id))
            fragmentCallback.callbackDataAcessSuccess(true);
        else
            fragmentCallback.callbackDataAcessError(false);
    }
}
