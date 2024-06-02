package net.ddns.djpinxo.warecontrol.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewUserFragment extends Fragment {


    private RecyclerView recyclerViewUser;
    private AdapterUserList adapter;
    //private List<User> userList = BBDDExample.getUsers();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_user, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerViewUser = view.findViewById(R.id.recyclerViewUser);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterUserList(MainActivity.userDao.getUsers(), getActivity());
        recyclerViewUser.setAdapter(adapter);


        view.findViewById(R.id.buttonInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertUserFragment insertUserFragment = new InsertUserFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, insertUserFragment);

            }

        });

    }
    /*
    private void addItem(User user) {
        userList.add(user);
        adapter.notifyItemInserted(userList.size() - 1);
    }*/
}