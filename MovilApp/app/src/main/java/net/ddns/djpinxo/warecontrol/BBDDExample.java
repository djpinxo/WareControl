/*package net.ddns.djpinxo.warecontrol;

import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class BBDDExample {

    private static List<User> users;

    public static List<User> getUsers() {
        if (users==null){
            users = new ArrayList<User>();
            int numeroUsuarios=40;
            for(int i=0; i<numeroUsuarios; i++){
                users.add(new User("usuario"+i+"@localhost.com", "usuario"+i, "contraseña"+i));
            }
        }
        return users;
    }

    public static User getUser(String email) {
        ArrayList <User> usersTemp = (ArrayList)getUsers();
        User result=null;
        for (User user : usersTemp){
            if(user.getEmail().equals(email)){
                result=user;
                break;
            }
        }

        return result;
    }

    public static User insertUser(User userTemp){
        User result = getUser(userTemp.getEmail());
        if (result == null && !userTemp.getEmail().trim().equals("")){
            getUsers().add(userTemp);
            result = userTemp;
        }
        return result;
    }

    public static User updateUser(User userTemp){
        User result = getUser(userTemp.getEmail());
        if (result != null){
            result.setNombre(userTemp.getNombre());
            result.setPassword(userTemp.getPassword());
        }
        return result;
    }

    public static boolean deleteUser(String email){
        boolean result=false;
        User userTemp = getUser(email);
        if (userTemp != null){
            getUsers().remove(userTemp);
            result=true;
        }
        return result;
    }

}
*/