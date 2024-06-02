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
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

public class InsertUserFragment extends Fragment implements FragmentCallback <User> {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
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
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerUser();
                /*((MainActivity)getActivity()).showConfirmationDialog();
                userModel=insertUser();
                if(userModel != null){
                    Toast.makeText(getContext(), R.string.user_inserted_dialog, Toast.LENGTH_LONG).show();
                    SelectUserFragment selectUserFragment=new SelectUserFragment(userModel);
                    ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
                }
                else {
                    Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
                }*/

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


/*
    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        //TODO realizar validaciones ventana

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Nombre es requerido");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Correo electrónico es requerido");
            return;
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword) || !password.equals(repeatPassword)) {
            editTextPassword.setError("Contraseña es requerida");
            return;
        }



        // Llamar a la API para registrar al usuario
        userModel = new User(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextEmail.getText().toString());
        if(MainActivity.userDao.getUser(userModel.getEmail())==null){
            MainActivity.userDao.getUsers().add(userModel);
            Toast.makeText(getContext(), "nuevo usuario insetado", Toast.LENGTH_LONG).show();
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.registerUser(name, email, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    // Navegar a otra actividad o realizar otra acción
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en el registro: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Fallo en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }*/

    private void insertUser(){

        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        if(validateUserForm()){
            userModel=new User(email,name,password);
            MainActivity.userDao.insertUser(this, userModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUserForm(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        boolean result=true;

        if(email.isEmpty()) {
            editTextEmail.setError(R.string.email + " " + R.string.required_dialog);
            result = false;
        }
        if(name.isEmpty()) {
            editTextName.setError(R.string.name + " " + R.string.required_dialog);
            result = false;
        }
        if(password.isEmpty()) {
            editTextPassword.setError(R.string.password + " " + R.string.required_dialog);
            result = false;
        }
        if(repeatPassword.isEmpty()) {
            editTextRepeatPassword.setError(R.string.repeatpassword + " " + R.string.required_dialog);
            result = false;
        }
        if(result && !password.equals(repeatPassword)) {
            editTextRepeatPassword.setError("la "+R.string.password+" tiene que coincidir en los dos campos");
            result = false;
        }

        return result;
    }


    @Override
    public void callbackDataAcessSuccess(User object) {
        Toast.makeText(getContext(), R.string.user_inserted_dialog, Toast.LENGTH_LONG).show();
        SelectUserFragment selectUserFragment=new SelectUserFragment(userModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
    }

    @Override
    public void callbackDataAcessError(User object) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}