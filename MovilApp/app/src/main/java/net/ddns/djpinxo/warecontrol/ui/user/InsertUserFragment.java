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

public class InsertUserFragment extends Fragment implements FragmentCallback <User> {

    private EditText editTextNombre;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private EditText editTextInsertDate;
    private EditText editTextLastLogin;
    private CheckBox checkBoxActive;
    private CheckBox checkBoxAdmin;
    private Button buttonInsert;
    private Button buttonCancel;
    private User userModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.user_insert_title);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        editTextInsertDate = view.findViewById(R.id.editTextInsertDate);
        editTextLastLogin = view.findViewById(R.id.editTextLastLogin);
        checkBoxActive = view.findViewById(R.id.checkBoxActive);
        checkBoxAdmin = view.findViewById(R.id.checkBoxAdmin);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUserFragment viewUserFragment=new ViewUserFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
            }

        });
    }

    private void insertUser(){
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();
        String insertDate = editTextInsertDate.getText().toString().trim();
        String lastLogin = editTextLastLogin.getText().toString().trim();
        boolean isActive = checkBoxActive.isChecked();
        boolean isAdmin = checkBoxAdmin.isChecked();

        if(validateUserForm()){
            password= HashUtils.hashString(password);
            userModel=new User(email, name, password, isAdmin, isActive);
            MainActivity.userDao.insertUser(this, userModel);
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
        String insertDate = editTextInsertDate.getText().toString().trim();
        String lastLogin = editTextLastLogin.getText().toString().trim();
        boolean isActive = checkBoxActive.isChecked();
        boolean isAdmin = checkBoxAdmin.isChecked();

        boolean result = true;
        if(email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email) + " " + getString(R.string.required_dialog));
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
        Toast.makeText(getContext(), R.string.user_inserted_dialog, Toast.LENGTH_LONG).show();
        userModel = user;
        SelectUserFragment selectUserFragment = new SelectUserFragment(userModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
    }

    @Override
    public void callbackDataAcessError(User user) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}