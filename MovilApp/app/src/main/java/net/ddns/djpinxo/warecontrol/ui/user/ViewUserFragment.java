package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewUserFragment extends Fragment {


    private RecyclerView recyclerView;
    private AdapterUserList adapter;
    private List<User> userList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void addItem(User user) {
        userList.add(user);
        adapter.notifyItemInserted(userList.size() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_user, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        adapter = new AdapterUserList(userList);
        recyclerView.setAdapter(adapter);

        // Agrega algunos elementos a la lista
        userList.add(new User("usuario1@localhost.com", "usuario1", "contraseña1"));
        userList.add(new User("usuario2@localhost.com", "usuario2", "contraseña2"));
        userList.add(new User("usuario3@localhost.com", "usuario3", "contraseña3"));
        userList.add(new User("usuario4@localhost.com", "usuario4", "contraseña4"));


        getView().findViewById(R.id.buttonAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(new User("usuario5@localhost.com", "usuario5", "contraseña5"));
                Toast.makeText(getContext().getApplicationContext(), "evento modificar pulsado", Toast.LENGTH_LONG).show();
            }

        });

    }
}