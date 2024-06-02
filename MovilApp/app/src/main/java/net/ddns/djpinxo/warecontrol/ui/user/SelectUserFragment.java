package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

public class SelectUserFragment extends Fragment implements FragmentCallback<User> {


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
        this.userModel=userModel;

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
                UpdateUserFragment updateUserFragment=new UpdateUserFragment(userModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateUserFragment);
            }

        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteUserFragment(userModel).showConfirmationDialog(getActivity());
            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUserFragment viewUserFragment=new ViewUserFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
            }

        });

        MainActivity.userDao.getUser(this, userModel.getEmail());
    }

    public void callbackDataAcessSuccess(User user){
        userModel = user;
        editTextEmail.setText(userModel.getEmail());
        editTextName.setText(userModel.getNombre());
        editTextPassword.setText(userModel.getPassword());
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
    }

    public void callbackDataAcessError(User user){
        ViewUserFragment viewUserFragment=new ViewUserFragment();
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
    }


}