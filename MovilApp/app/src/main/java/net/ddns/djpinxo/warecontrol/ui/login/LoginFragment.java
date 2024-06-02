package net.ddns.djpinxo.warecontrol.ui.login;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.utils.HashUtils;

public class LoginFragment implements FragmentCallback<User> {

    //creado por mantener una estructura aun que no sea fragment

    private Activity activity;

    private EditText editTextEmail;
    private EditText editTextPassword;


    public void sendLogin(Activity activity) {
        this.activity=activity;

        editTextEmail = activity.findViewById(R.id.editTextEmail);
        editTextPassword = activity.findViewById(R.id.editTextPassword);

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(validateLoginForm()){
            password = HashUtils.hashString(password);
            User userModel=new User(email, null, password);
            MainActivity.loginDao.loginUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateLoginForm() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        boolean result = true;
        if(email.isEmpty()) {
            editTextEmail.setError(activity.getString(R.string.email) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }
        if(password.isEmpty()) {
            editTextPassword.setError(activity.getString(R.string.password) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(User user) {
        MainActivity.userLogin = user;
        Toast.makeText(activity, R.string.welcome, Toast.LENGTH_LONG).show();
        ((MainActivity) activity).loadMainActivityLayout();


    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}