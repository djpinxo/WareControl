package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalScanContenedor;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalSelectContenedor;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalViewContenedor;

import java.util.ArrayList;

public class InsertItemFragment extends Fragment implements FragmentCallback <Item> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedor;
    private ImageButton buttonImagen;
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
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_insert_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedor = view.findViewById(R.id.editTextContenedor);
        buttonImagen = view.findViewById(R.id.buttonImagen);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        ImageButton buttonInfo = view.findViewById(R.id.buttonInfo);
        ImageButton buttonSearch = view.findViewById(R.id.buttonSearch);
        ImageButton buttonScan = view.findViewById(R.id.buttonScan);
        ImageButton buttonImagen = view.findViewById(R.id.buttonImagen);
        buttonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Long.valueOf(editTextContenedor.getText().toString().trim());
                    showSelectModal();
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), getString(R.string.container)+ " " + getString(R.string.notnumeric_dialog), Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListModal();
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    showScanModal();
                } else {
                    requestCameraPermission();
                }
                //showScanModal();
            }
        });
        buttonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    showCaptureModal();
                } else {
                    requestCameraPermission();
                }
            }
        });

    }

    //crear modal de informacion de contenedor
    private void showSelectModal() {
        //TODO cambiar dialog a una clase
        ModalSelectContenedor modalSelectContenedor = new ModalSelectContenedor(new Contenedor(Long.valueOf(editTextContenedor.getText().toString().trim()),null,null,null,null,null));
        modalSelectContenedor.show(getParentFragmentManager(), "ContenedorSelect");
/*
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modal_select_contenedor, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Informacion del contenedor");
        builder.setView(dialogView);
        builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();

        // Mostrar el diálogo

        if(!editTextContenedor.getText().toString().trim().isEmpty()){
            dialog.show();

            new ContenedorModalSelect(new Contenedor(Long.valueOf(editTextContenedor.getText().toString().trim()),null,null,null,null,null), getActivity(), dialog);
        }
        */


    }
    //finish crear modal

    //crear modal de seleccion de contenedor
    private void showListModal() {
        ModalViewContenedor modalViewContenedor = new ModalViewContenedor();
        //contenedorModalScan.setTargetFragment(this, 1);
        modalViewContenedor.show(getParentFragmentManager(), "ContenedorView");

/*
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.modal_list, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona un elemento");
        builder.setView(dialogView);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();

        RecyclerView recyclerViewModal = dialogView.findViewById(R.id.recyclerViewModal);
        recyclerViewModal.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterContenedorModalList adapter = new AdapterContenedorModalList(new ArrayList<Contenedor>(), getActivity(), dialog);
        recyclerViewModal.setAdapter(adapter);

        // Mostrar el diálogo
        dialog.show();


        MainActivity.contenedorDao.getContenedores(adapter);*/
    }
    //finish crear modal

    //crear modal de scan qa de contenedor
    private void showScanModal() {
        ModalScanContenedor modalScanContenedor = new ModalScanContenedor();
        //contenedorModalScan.setTargetFragment(this, 1);
        modalScanContenedor.show(getParentFragmentManager(), "ContenedorScan");
    }
    private void showCaptureModal() {
        ModalInsertItemImagenFragment modalInsertItemImagenFragment = new ModalInsertItemImagenFragment(itemModel, getActivity(), buttonImagen);
        modalInsertItemImagenFragment.show(getParentFragmentManager(), "ItemCapture");
    }
    private boolean checkCameraPermission() {
        return getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
    }
    //finish crear modal

    private void insertItem(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        if(validateItemForm()){
            Contenedor contenedorPadre = (sIdContenedor.isEmpty())?null:new Contenedor(Long.valueOf(sIdContenedor), null, null, null, null, null);
            itemModel=new Item(0l,name,descripcion, contenedorPadre);
            MainActivity.itemDao.insertItem(this, itemModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        boolean result=true;
        if(name.isEmpty()) {
            editTextNombre.setError(getString(R.string.name) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(!sIdContenedor.isEmpty()){
            try{
                Long.valueOf(sIdContenedor);
            }catch (NumberFormatException e) {
                editTextContenedor.setError(getString(R.string.container)+ " " + getString(R.string.notnumeric_dialog));
                result = false;
            }
        }

        return result;
    }

    @Override
    public void callbackDataAcessSuccess(Item item) {
        Toast.makeText(getContext(), R.string.item_inserted_dialog, Toast.LENGTH_LONG).show();
        itemModel=item;
        if(buttonImagen.getContentDescription().equals("imagen modificada")){
            new ModalInsertItemImagenFragment(item, getActivity(), buttonImagen).insertItemImagen();
        }
        SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
        ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
    }

    @Override
    public void callbackDataAcessError(Item item) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}