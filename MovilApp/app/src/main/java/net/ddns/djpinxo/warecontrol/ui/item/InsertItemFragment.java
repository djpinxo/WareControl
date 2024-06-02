package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

public class InsertItemFragment extends Fragment implements FragmentCallback <Item> {

    private EditText editTextNombre;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextRepeatPassword;
    private Button buttonInsert;
    private Button buttonCancel;
    private Item itemModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_insert_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerItem();
                /*((MainActivity)getActivity()).showConfirmationDialog();
                itemModel=insertItem();
                if(itemModel != null){
                    Toast.makeText(getContext(), R.string.item_inserted_dialog, Toast.LENGTH_LONG).show();
                    SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
                    ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
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
                        insertItem();
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
                ViewItemFragment viewItemFragment=new ViewItemFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewItemFragment);
            }

        });
    }


/*
    private void registerItem() {
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        //TODO realizar validaciones ventana

        if (TextUtils.isEmpty(name)) {
            editTextNombre.setError("Nombre es requerido");
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
        itemModel = new Item(editTextEmail.getText().toString(), editTextNombre.getText().toString(), editTextEmail.getText().toString());
        if(MainActivity.itemDao.getItem(itemModel.getEmail())==null){
            MainActivity.itemDao.getItems().add(itemModel);
            Toast.makeText(getContext(), "nuevo usuario insetado", Toast.LENGTH_LONG).show();
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.registerItem(name, email, password);
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

    private void insertItem(){

        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        if(validateItemForm()){
            //itemModel=new Item(email,name,password);
            MainActivity.itemDao.insertItem(this, itemModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm(){
        String name = editTextNombre.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        boolean result=true;

        if(email.isEmpty()) {
            editTextEmail.setError(R.string.email + " " + R.string.required_dialog);
            result = false;
        }
        if(name.isEmpty()) {
            editTextNombre.setError(R.string.name + " " + R.string.required_dialog);
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
    public void callbackDataAcessSuccess(Item item) {
        //Toast.makeText(getContext(), R.string.item_inserted_dialog, Toast.LENGTH_LONG).show();
        itemModel=item;
        SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
    }

    @Override
    public void callbackDataAcessError(Item item) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}