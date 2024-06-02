package net.ddns.djpinxo.warecontrol.data.daos;

import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.List;

public interface ContenedorDao {

    public void getContenedores(FragmentCallback<List<Contenedor>> fragmentCallback);
    public void getContenedor(FragmentCallback<Contenedor> fragmentCallback, long id);
    public void insertContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor);
    public void updateContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor);
    public void deleteContenedor(FragmentCallback<Boolean> fragmentCallback, long id);
    public void getContenedorItems(FragmentCallback<List<Item>> fragmentCallback, long id);
    public void getContenedorContenedorHijos(FragmentCallback<List<Contenedor>> fragmentCallback, long id);
}
