package net.ddns.djpinxo.warecontrol.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.utils.HashUtils;

public class RegisterUserFragment implements FragmentCallback <User> {

    private Activity activity;
    private EditText editTextNombre;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private User userModel;


    public void showConfirmationDialog(Activity activity) {
        this.activity=activity;

        editTextNombre = activity.findViewById(R.id.editTextNombre);
        editTextEmail = activity.findViewById(R.id.editTextEmail);
        editTextPassword = activity.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = activity.findViewById(R.id.editTextRepeatPassword);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_dialog);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertUser();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void insertUser(){
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        if(validateUserForm()){
            password= HashUtils.hashString(password);
            userModel=new User(email, name, password, false, false);
            MainActivity.loginDao.registerUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUserForm(){
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        boolean result = true;
        if(email.isEmpty()) {
            editTextEmail.setError(activity.getString(R.string.email) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }
        if(name.isEmpty()) {
            editTextNombre.setError(activity.getString(R.string.name) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }
        if(password.isEmpty()) {
            editTextPassword.setError(activity.getString(R.string.password) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }
        if(repeatPassword.isEmpty()) {
            editTextRepeatPassword.setError(activity.getString(R.string.repeatpassword) + " " + activity.getString(R.string.required_dialog));
            result = false;
        }
        if(result && !password.equals(repeatPassword)) {
            editTextRepeatPassword.setError("la "+activity.getString(R.string.password)+" tiene que coincidir en los dos campos");
            result = false;
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(User user) {
        userModel=null;
        MainActivity.userLogin=null;
        Toast.makeText(activity, R.string.user_inserted_dialog, Toast.LENGTH_LONG).show();
        ((MainActivity) activity).loadLoginLayout();
    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}