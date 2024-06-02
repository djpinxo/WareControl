package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class ViewContenedorFragment extends Fragment implements FragmentCallback<List<Contenedor>> {


    private RecyclerView recyclerViewContenedor;
    private AdapterContenedorList adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_contenedor, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerViewContenedor = view.findViewById(R.id.recyclerViewContenedor);
        recyclerViewContenedor.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdapterContenedorList(new ArrayList<Contenedor>(), getActivity());
        recyclerViewContenedor.setAdapter(adapter);


        view.findViewById(R.id.buttonInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertContenedorFragment insertContenedorFragment = new InsertContenedorFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, insertContenedorFragment);

            }

        });

        MainActivity.contenedorDao.getContenedores(this);

    }

    @Override
    public void callbackDataAcessSuccess(List<Contenedor> contenedores) {
        adapter.updateData(contenedores);
    }

    @Override
    public void callbackDataAcessError(List<Contenedor> contenedores) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }

}