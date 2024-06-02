package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

public class UpdateUserFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private Button buttonUpdate;
    private Button buttonCancel;
    private User userModel;

    public UpdateUserFragment(){
        super();
    }
    public UpdateUserFragment (User userModel){
        this();
        this.userModel = MainActivity.userDao.getUser(userModel.getEmail());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_user, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showConfirmationDialog();
                //registerUser();
                //TODO corregir si no hay user en bbdd da error
                userModel = updateUser();
                if(userModel!=null){
                    Toast.makeText(getContext(), R.string.user_updated_dialog, Toast.LENGTH_LONG).show();
                    SelectUserFragment selectUserFragment=new SelectUserFragment(userModel);
                    ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
                }
                else {
                    Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
                }
            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectUserFragment selectUserFragment=new SelectUserFragment(userModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectUserFragment);
            }

        });

        editTextEmail.setText(userModel.getEmail());
        editTextName.setText(userModel.getNombre());
        editTextPassword.setText(userModel.getPassword());
    }



    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();


        /*
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
        }*/



        // Llamar a la API para registrar al usuario
        //userModel = new User(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextEmail.getText().toString());
        userModel = MainActivity.userDao.getUser(editTextEmail.getText().toString());
        if(userModel!=null) {
            userModel.setNombre(editTextName.getText().toString());
            userModel.setPassword(editTextPassword.getText().toString());
            Toast.makeText(getContext(), "usuario modificado", Toast.LENGTH_LONG).show();
        }
        /*
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
        });*/

    }

    private User updateUser(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        if(validateUserForm()){
            userModel=new User(email,name,password);
            return MainActivity.userDao.updateUser(userModel);
        }
        else {
            return null;
        }
    }

    private boolean validateUserForm() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        boolean result = true;
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
}