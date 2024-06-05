package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.item.ViewItemFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewContenedorFragment extends Fragment implements FragmentCallback<List<Contenedor>> {


    private RecyclerView recyclerViewContenedor;
    private AdapterContenedorList adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private EditText textSearch;
    private ImageButton buttonSearch;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_contenedor, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.contenedor_view_title);

        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout);
        textSearch = view.findViewById(R.id.textSearch);
        buttonSearch = view.findViewById(R.id.buttonSearch);

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

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textSearch.getText().toString().trim().equals(""))
                    MainActivity.contenedorDao.getContenedores(ViewContenedorFragment.this, textSearch.getText().toString().trim());
                else
                    MainActivity.contenedorDao.getContenedores(ViewContenedorFragment.this);
            }
        });
        ImageButton buttonScan = view.findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    showScanModal();
                } else {
                    requestCameraPermission();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!textSearch.getText().toString().trim().equals(""))
                    MainActivity.contenedorDao.getContenedores(ViewContenedorFragment.this, textSearch.getText().toString().trim());
                else
                    MainActivity.contenedorDao.getContenedores(ViewContenedorFragment.this);

            }
        });

    }
    //crear modal de scan qa de contenedor
    private void showScanModal() {
        ModalScanContenedor modalScanContenedor = new ModalScanContenedor(true);
        modalScanContenedor.show(getParentFragmentManager(), "ContenedorScan");
    }
    private boolean checkCameraPermission() {
        return getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
    }
    //finish crear modal

    @Override
    public void callbackDataAcessSuccess(List<Contenedor> contenedores) {
        adapter.updateData(contenedores);
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void callbackDataAcessError(List<Contenedor> contenedores) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}