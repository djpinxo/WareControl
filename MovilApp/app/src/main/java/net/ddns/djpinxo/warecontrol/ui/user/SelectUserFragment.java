package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

public class SelectUserFragment extends Fragment {


    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonBack;
    private User userModel;

    public SelectUserFragment (){
        super();
    }
    public SelectUserFragment (User userModel){
        this();
        this.userModel = MainActivity.userDaoApi.getUser(userModel.getEmail());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_user, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonBack = view.findViewById(R.id.buttonBack);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BBDDExample.getUsers().add(new User(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextEmail.getText().toString()));
                //Toast.makeText(getContext(), "nuevo usuario insetado", Toast.LENGTH_LONG).show();
                UpdateUserFragment updateUserFragment=new UpdateUserFragment(userModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateUserFragment);
            }

        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeleteUserFragment(userModel).showConfirmationDialog(getActivity());
                //BBDDExample.getUsers().add(new User(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextEmail.getText().toString()));
                //Toast.makeText(getContext(), "nuevo usuario insetado", Toast.LENGTH_LONG).show();

                //ViewUserFragment viewUserFragment=new ViewUserFragment();

                //((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BBDDExample.getUsers().add(new User(editTextEmail.getText().toString(), editTextName.getText().toString(), editTextEmail.getText().toString()));
                //Toast.makeText(getContext(), "nuevo usuario insetado", Toast.LENGTH_LONG).show();

                ViewUserFragment viewUserFragment=new ViewUserFragment();

                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
            }

        });

        editTextEmail.setText(userModel.getEmail());
        editTextName.setText(userModel.getNombre());
        editTextPassword.setText(userModel.getPassword());
    }





}