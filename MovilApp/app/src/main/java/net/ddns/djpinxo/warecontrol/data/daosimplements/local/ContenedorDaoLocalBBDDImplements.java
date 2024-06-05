package net.ddns.djpinxo.warecontrol.data.daosimplements.local;

import android.util.Log;

import net.ddns.djpinxo.warecontrol.data.daos.ContenedorDao;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ContenedorDaoLocalBBDDImplements implements ContenedorDao {

    private static List<Contenedor> contenedores;

    public ContenedorDaoLocalBBDDImplements(){
        super();
        if (contenedores==null){
            contenedores = new ArrayList<Contenedor>();
            int numeroUsuarios=40;
            for(long l=0; l<numeroUsuarios; l++){
                contenedores.add(new Contenedor(l, "contenedor"+l, "descripcion del contenedor "+l, null, null, null));
            }
        }
    }


    public List<Contenedor> getContenedores() {
        return contenedores;
    }

    public Contenedor getContenedor(long id) {
        List <Contenedor> contenedoresTemp = getContenedores();
        Contenedor result=null;
        for (Contenedor contenedor : contenedoresTemp){
            if(contenedor.getId().equals(id)){
                result=contenedor;
                break;
            }
        }

        return result;
    }

    public Contenedor insertContenedor(Contenedor contenedorTemp){
        Contenedor result = getContenedor(contenedorTemp.getId());
        if (result == null){
            getContenedores().add(contenedorTemp);
            return contenedorTemp;
        }
        else {
            return null;
        }
        //return result;
    }

    public Contenedor updateContenedor(Contenedor contenedorTemp){
        Contenedor result = getContenedor(contenedorTemp.getId());
        if (result != null){
            result.setId(contenedorTemp.getId());
            result.setNombre(contenedorTemp.getNombre());
            result.setDescripcion(contenedorTemp.getDescripcion());
            result.setContenedorPadre(contenedorTemp.getContenedorPadre());
        }
        return result;
    }

    public boolean deleteContenedor(long id){
        boolean result=false;
        Contenedor contenedorTemp = getContenedor(id);
        if (contenedorTemp != null){
            getContenedores().remove(contenedorTemp);
            result=true;
        }
        return result;
    }


    @Override
    public void getContenedores(FragmentCallback<List<Contenedor>> fragmentCallback) {
        fragmentCallback.callbackDataAcessSuccess(getContenedores());
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();

            }
        });
        hilo.start();
    }

    @Override
    public void getContenedores(FragmentCallback<List<Contenedor>> fragmentCallback, String query) {

    }

    @Override
    public void getContenedor(FragmentCallback<Contenedor> fragmentCallback, long id) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                Contenedor contenedorResult = getContenedor(id);
                if (contenedorResult != null)
                    fragmentCallback.callbackDataAcessSuccess(contenedorResult);
                else
                    fragmentCallback.callbackDataAcessError(contenedorResult);
            }
        });
        hilo.start();
    }

    @Override
    public void insertContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                Contenedor contenedorResult = insertContenedor(contenedor);
                if(contenedorResult != null)
                    fragmentCallback.callbackDataAcessSuccess(contenedorResult);
                else
                    fragmentCallback.callbackDataAcessError(contenedorResult);

            }
        });
        hilo.start();
    }

    @Override
    public void updateContenedor(FragmentCallback<Contenedor> fragmentCallback, Contenedor contenedor) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                Contenedor contenedorResult = updateContenedor(contenedor);
                if(contenedorResult != null)
                    fragmentCallback.callbackDataAcessSuccess(contenedorResult);
                else
                    fragmentCallback.callbackDataAcessError(contenedorResult);
            }
        });
        hilo.start();
    }

    @Override
    public void deleteContenedor(FragmentCallback<Boolean> fragmentCallback, long id) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                if(deleteContenedor(id))
                    fragmentCallback.callbackDataAcessSuccess(true);
                else
                    fragmentCallback.callbackDataAcessError(false);
            }
        });
        hilo.start();
    }

    @Override
    public void getContenedorItems(FragmentCallback<List<Item>> fragmentCallback, long id) {

    }

    @Override
    public void getContenedorContenedorHijos(FragmentCallback<List<Contenedor>> fragmentCallback, long id) {

    }

    public static void waitSimulation(){
        for (int i =0; i<100000; i++)
            for (int a =0; a<10000; a++);
    }
}


