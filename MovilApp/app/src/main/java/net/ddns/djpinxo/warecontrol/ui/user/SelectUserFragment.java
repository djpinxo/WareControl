package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.ui.item.SelectItemFragment;

public class SelectUserFragment extends Fragment implements FragmentCallback<User> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText editTextNombre;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextInsertDate;
    private EditText editTextLastLogin;
    private EditText editTextUpdateDate;
    private CheckBox checkBoxActive;
    private CheckBox checkBoxAdmin;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonBack;
    private static User userModel;

    public SelectUserFragment (){
        super();
    }
    public SelectUserFragment (User userModel){
        this();
        this.userModel=userModel;
        MainActivity.userDao.getUser(this, userModel.getEmail());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.user_select_title);

        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextInsertDate = view.findViewById(R.id.editTextInsertDate);
        editTextLastLogin = view.findViewById(R.id.editTextLastLogin);
        editTextUpdateDate = view.findViewById(R.id.editTextUpdateDate);
        checkBoxActive = view.findViewById(R.id.checkBoxActive);
        checkBoxAdmin = view.findViewById(R.id.checkBoxAdmin);
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.userDao.getUser(SelectUserFragment.this, userModel.getEmail());

            }
        });

        //MainActivity.userDao.getUser(this, userModel.getEmail());
    }

    public void callbackDataAcessSuccess(User user){
        userModel = user;
        editTextEmail.setText(userModel.getEmail());
        editTextNombre.setText(userModel.getNombre());
        editTextPassword.setText(userModel.getPassword());
        editTextInsertDate.setText(userModel.getInsertDate());
        editTextLastLogin.setText(userModel.getLastLogin());
        editTextUpdateDate.setText(userModel.getUpdateDate());
        checkBoxActive.setChecked(userModel.isActive());
        checkBoxAdmin.setChecked(userModel.isAdmin());
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
    }

    public void callbackDataAcessError(User user){
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
        ViewUserFragment viewUserFragment=new ViewUserFragment();
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewUserFragment);
    }
}