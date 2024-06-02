package net.ddns.djpinxo.warecontrol.ui.contenedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;

public class SelectContenedorFragment extends Fragment implements FragmentCallback<Contenedor> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedorPadre;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonBack;
    private static Contenedor contenedorModel;

    public SelectContenedorFragment (){
        super();
    }
    public SelectContenedorFragment (Contenedor contenedorModel){
        this();
        this.contenedorModel=contenedorModel;
        MainActivity.contenedorDao.getContenedor(this, contenedorModel.getId());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_contenedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.contenedor_select_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedorPadre = view.findViewById(R.id.editTextContenedorPadre);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonBack = view.findViewById(R.id.buttonBack);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateContenedorFragment updateContenedorFragment=new UpdateContenedorFragment(contenedorModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, updateContenedorFragment);
            }

        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteContenedorFragment(contenedorModel).showConfirmationDialog(getActivity());
            }

        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
            }

        });

        ImageButton buttonInfo = view.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextContenedorPadre.getText().toString().trim());
                    showSelectModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.dadcontainer)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });
        Button buttonHijos = view.findViewById(R.id.buttonHijos);
        buttonHijos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextId.getText().toString().trim());
                    showChildsModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.id)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });
        Button buttonItems = view.findViewById(R.id.buttonItems);
        buttonItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextId.getText().toString().trim());
                    showItemsModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.id)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //crear modal de informacion de contenedor
    private void showSelectModal() {
        ModalSelectContenedor modalSelectContenedor = new ModalSelectContenedor(new Contenedor(Long.valueOf(editTextContenedorPadre.getText().toString().trim()),null,null,null,null,null));
        modalSelectContenedor.show(getParentFragmentManager(), "ContenedorSelect");
    }
    //finish crear modal

    //crear modal de lista hijos
    private void showChildsModal() {
        ModalViewContenedorContenedorHijos modalViewContenedorContenedorHijos = new ModalViewContenedorContenedorHijos(new Contenedor(Long.valueOf(editTextId.getText().toString().trim()),null,null,null,null,null));
        modalViewContenedorContenedorHijos.show(getParentFragmentManager(), "ContenedorChilds");
    }
    //finish crear modal

    //crear modal de lista de items
    private void showItemsModal() {
        ModalViewContenedorItems modalViewContenedorItems = new ModalViewContenedorItems(new Contenedor(Long.valueOf(editTextId.getText().toString().trim()),null,null,null,null,null));
        modalViewContenedorItems.show(getParentFragmentManager(), "ContenedorItems");
    }
    //finish crear modal


    public void callbackDataAcessSuccess(Contenedor contenedor){
        contenedorModel = contenedor;
        editTextId.setText(contenedorModel.getId().toString());
        editTextNombre.setText(contenedorModel.getNombre());
        editTextDescripcion.setText(contenedorModel.getDescripcion());
        if(contenedorModel.getContenedorPadre()!=null) {
            editTextContenedorPadre.setText(contenedorModel.getContenedorPadre().getId().toString());
        }
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
    }

    public void callbackDataAcessError(Contenedor contenedor){
        ViewContenedorFragment viewContenedorFragment=new ViewContenedorFragment();
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, viewContenedorFragment);
    }
}