package net.ddns.djpinxo.warecontrol.ui;


import net.ddns.djpinxo.warecontrol.data.model.User;

public interface FragmentCallback <T>{


    public void callbackDataAcessSuccess(T object);
    public void callbackDataAcessError(T object);

}
