package net.ddns.djpinxo.warecontrol.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.utils.HashUtils;

public class UpdateUserFragment extends Fragment implements FragmentCallback<User> {

    private EditText editTextNombre;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private EditText editTextInsertDate;
    private EditText editTextLastLogin;
    private CheckBox checkBoxActive;
    private CheckBox checkBoxAdmin;
    private Button buttonUpdate;
    private Button buttonCancel;
    private static User userModel;
    private boolean isUpdating=false;

    public UpdateUserFragment(){
        super();
    }
    public UpdateUserFragment (User userModel){
        this();
        this.userModel=userModel;
        isUpdating=false;
        MainActivity.userDao.getUser(this, userModel.getEmail());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.user_update_title);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        editTextInsertDate = view.findViewById(R.id.editTextInsertDate);
        editTextLastLogin = view.findViewById(R.id.editTextLastLogin);
        checkBoxActive = view.findViewById(R.id.checkBoxActive);
        checkBoxAdmin = view.findViewById(R.id.checkBoxAdmin);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUser();
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

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUserFragment selectUserFragment=new SelectUserFragment(userModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
            }

        });
    }

    private void updateUser(){
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();
        String insertDate = editTextInsertDate.getText().toString().trim();
        String lastLogin = editTextLastLogin.getText().toString().trim();
        boolean isActive = checkBoxActive.isChecked();
        boolean isAdmin = checkBoxAdmin.isChecked();

        if(validateUserForm()){
            //userModel=new User(email, name, password, isAdmin, isActive);
            userModel.setNombre(name);
            //Se verifica si la contraseña ha cambiado, si no lo hizo no volver a lanzar el hash
            if(!password.equals(userModel.getPassword()))
                password = HashUtils.hashString(password);
            userModel.setPassword(password);
            userModel.setActive(isActive);
            userModel.setAdmin(isAdmin);
            isUpdating=true;
            MainActivity.userDao.updateUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUserForm() {
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();
        String insertDate = editTextInsertDate.getText().toString().trim();
        String lastLogin = editTextLastLogin.getText().toString().trim();
        boolean isActive = checkBoxActive.isChecked();
        boolean isAdmin = checkBoxAdmin.isChecked();

        boolean result = true;
        if(email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(!email.equals(userModel.getEmail())) {
            editTextEmail.setError(getString(R.string.email) + " " + "introducido no concuerda con el original");
            result = false;
        }
        if(name.isEmpty()) {
            editTextNombre.setError(getString(R.string.name) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(password.isEmpty()) {
            editTextPassword.setError(getString(R.string.password) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(repeatPassword.isEmpty()) {
            editTextRepeatPassword.setError(getString(R.string.repeatpassword) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(result && !password.equals(repeatPassword)) {
            editTextRepeatPassword.setError("la "+getString(R.string.password)+" tiene que coincidir en los dos campos");
            result = false;
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(User user) {
        userModel = user;
        editTextEmail.setText(userModel.getEmail());
        editTextNombre.setText(userModel.getNombre());
        editTextPassword.setText(userModel.getPassword());
        editTextRepeatPassword.setText(userModel.getPassword());
        editTextInsertDate.setText(userModel.getInsertDate());
        editTextLastLogin.setText(userModel.getLastLogin());
        checkBoxActive.setChecked(userModel.isActive());
        checkBoxAdmin.setChecked(userModel.isAdmin());
        if(isUpdating) {
            Toast.makeText(getContext(), R.string.user_updated_dialog, Toast.LENGTH_LONG).show();
            SelectUserFragment selectUserFragment = new SelectUserFragment(userModel);
            ((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
        }
        else if (!isUpdating) {
            ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
        }
        isUpdating=false;
    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}