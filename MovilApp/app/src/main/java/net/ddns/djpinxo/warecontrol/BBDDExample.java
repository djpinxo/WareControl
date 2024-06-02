package net.ddns.djpinxo.warecontrol;

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

}
