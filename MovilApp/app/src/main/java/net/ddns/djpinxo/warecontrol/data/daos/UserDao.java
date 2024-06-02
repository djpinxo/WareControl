package net.ddns.djpinxo.warecontrol.data.daos;

import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.List;

public interface UserDao {

    public List<User> getUsers();
    public User getUser(String email);
    public User insertUser(User user);
    public User updateUser(User user);
    public boolean deleteUser(String email);
}
