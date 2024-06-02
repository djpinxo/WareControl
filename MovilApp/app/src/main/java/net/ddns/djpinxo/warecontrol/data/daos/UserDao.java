package net.ddns.djpinxo.warecontrol.data.daos;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

public interface UserDao {

    public void getUsers(FragmentCallback<List<User>> fragmentCallback);
    public void getUser(FragmentCallback<User> fragmentCallback, String email);
    public void insertUser(FragmentCallback<User> fragmentCallback, User user);
    public void updateUser(FragmentCallback<User> fragmentCallback, User user);
    public void deleteUser(FragmentCallback<Boolean> fragmentCallback, String email);
}
