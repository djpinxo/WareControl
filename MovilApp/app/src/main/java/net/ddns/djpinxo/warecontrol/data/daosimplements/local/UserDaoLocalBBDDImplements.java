package net.ddns.djpinxo.warecontrol.data.daosimplements.local;

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
            result = userTemp;
        }
        return result;
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
}
