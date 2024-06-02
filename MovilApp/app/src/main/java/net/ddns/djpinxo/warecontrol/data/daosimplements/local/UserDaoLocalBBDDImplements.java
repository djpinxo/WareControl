package net.ddns.djpinxo.warecontrol.data.daosimplements.local;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.data.daos.UserDao;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoLocalBBDDImplements implements UserDao {

    private static List<User> users;

    public UserDaoLocalBBDDImplements(){
        super();
        if (users==null){
            users = new ArrayList<User>();
            int numeroUsuarios=40;
            for(int i=0; i<numeroUsuarios; i++){
                users.add(new User("usuario"+i+"@localhost.com", "usuario"+i, "contraseña"+i));
            }
        }
    }


    public List<User> getUsers() {
        return users;
    }

    public User getUser(String email) {
        List <User> usersTemp = getUsers();
        User result=null;
        for (User user : usersTemp){
            if(user.getEmail().equals(email)){
                result=user;
                break;
            }
        }

        return result;
    }

    public User insertUser(User userTemp){
        User result = getUser(userTemp.getEmail());
        if (result == null && !userTemp.getEmail().trim().equals("")){
            getUsers().add(userTemp);
            return userTemp;
        }
        else {
            return null;
        }
        //return result;
    }

    public User updateUser(User userTemp){
        User result = getUser(userTemp.getEmail());
        if (result != null){
            result.setNombre(userTemp.getNombre());
            result.setPassword(userTemp.getPassword());
        }
        return result;
    }

    public boolean deleteUser(String email){
        boolean result=false;
        User userTemp = getUser(email);
        if (userTemp != null){
            getUsers().remove(userTemp);
            result=true;
        }
        return result;
    }


    @Override
    public void getUsers(FragmentCallback<List<User>> fragmentCallback) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                fragmentCallback.callbackDataAcessSuccess(getUsers());
            }
        });
        hilo.start();
        fragmentCallback.callbackDataAcessSuccess(getUsers());
    }

    @Override
    public void getUser(FragmentCallback<User> fragmentCallback, String email) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                User userResult = getUser(email);
                if(userResult != null)
                    fragmentCallback.callbackDataAcessSuccess(userResult);
                else
                    fragmentCallback.callbackDataAcessError(userResult);
            }
        });
        hilo.start();
    }

    @Override
    public void insertUser(FragmentCallback<User> fragmentCallback, User user) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                User userResult = insertUser(user);
                if(userResult != null)
                    fragmentCallback.callbackDataAcessSuccess(userResult);
                else
                    fragmentCallback.callbackDataAcessError(userResult);
            }
        });
        hilo.start();
    }

    @Override
    public void updateUser(FragmentCallback<User> fragmentCallback, User user) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                User userResult = updateUser(user);
                if(userResult != null)
                    fragmentCallback.callbackDataAcessSuccess(userResult);
                else
                    fragmentCallback.callbackDataAcessError(userResult);
            }
        });
        hilo.start();
    }

    @Override
    public void deleteUser(FragmentCallback<Boolean> fragmentCallback, String email) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                ContenedorDaoLocalBBDDImplements.waitSimulation();
                if(deleteUser(email))
                    fragmentCallback.callbackDataAcessSuccess(true);
                else
                    fragmentCallback.callbackDataAcessError(false);
            }
        });
        hilo.start();
    }

    public static void waitSimulation(){
        for (int i =0; i<100000; i++)
            for (int a =0; a<10000; a++);
    }
}
