package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.ddns.djpinxo.warecontrol.data.model.User;
import net.ddns.djpinxo.warecontrol.databinding.FragmentLoginBinding;
import net.ddns.djpinxo.warecontrol.databinding.FragmentUserBinding;
import net.ddns.djpinxo.warecontrol.ui.login.LoginViewModel;
import net.ddns.djpinxo.warecontrol.ui.login.LoginViewModelFactory;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    //TODO borrar info dummy
    private ArrayList <User> users;

    public  UserFragment (){
        super();
        users = new ArrayList<User>();

        users.add(new User("usuario1@localhost.com", "usuario1", "contraseña1"));
        users.add(new User("usuario2@localhost.com", "usuario2", "contraseña2"));
        users.add(new User("usuario3@localhost.com", "usuario3", "contraseña3"));
        users.add(new User("usuario4@localhost.com", "usuario4", "contraseña4"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        Toast.makeText(getContext().getApplicationContext(), "onCreateView", Toast.LENGTH_LONG).show();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext().getApplicationContext(), "onViewCreated", Toast.LENGTH_LONG).show();
        //loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
