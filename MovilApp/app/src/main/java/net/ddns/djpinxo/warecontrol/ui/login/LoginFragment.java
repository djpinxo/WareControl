/*package net.ddns.djpinxo.warecontrol.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;

public class LoginFragment implements FragmentCallback<User> {

    //creado por mantener una estructura aun que no sea fragment

    Activity activity;

    private User userLogin;

    public LoginFragment(){
        super();
    }
    public LoginFragment (User userLogin){
        this();
        this.userLogin=userLogin;

    }

    public void sendLogin(){
        String email = userLogin.getEmail().toString().trim();
        String password = userLogin.getPassword().toString().trim();

        if(validateUserForm()){
            User userModel=new User(email, null, password);
            MainActivity.loginDao.loginUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUserForm() {
        String email = userLogin.getEmail().toString().trim();
        String password = userLogin.getPassword().toString().trim();

        boolean result = true;
        if(email.isEmpty()) {
            //editTextEmail.setError(R.string.email + " " + R.string.required_dialog);
            result = false;
        }
        if(password.isEmpty()) {
            //editTextPassword.setError(R.string.password + " " + R.string.required_dialog);
            result = false;
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(User user) {
        userLogin = user;
        //editTextEmail.setText(userModel.getEmail());
        //editTextNombre.setText(userModel.getNombre());
        //editTextPassword.setText(userModel.getPassword());
        Toast.makeText(activity.getBaseContext(), R.string.welcome, Toast.LENGTH_LONG).show();
        //SelectUserFragment selectUserFragment = new SelectUserFragment(userModel);
        //((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
        ((MainActivity)activity).loadMainActivityLayout();


    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(activity.getBaseContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}
*/