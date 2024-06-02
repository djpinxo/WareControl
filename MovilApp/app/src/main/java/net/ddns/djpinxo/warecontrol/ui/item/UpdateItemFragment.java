package net.ddns.djpinxo.warecontrol.ui.item;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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

import net.ddns.djpinxo.warecontrol.data.model.Contenedor;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalScanContenedor;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalSelectContenedor;
import net.ddns.djpinxo.warecontrol.ui.contenedor.ModalViewContenedor;

public class UpdateItemFragment extends Fragment implements FragmentCallback<Item> {

    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextContenedor;
    private ImageButton buttonImagen;
    private Button buttonUpdate;
    private Button buttonCancel;
    private static Item itemModel;
    private boolean isUpdating=false;

    public UpdateItemFragment(){
        super();
    }
    public UpdateItemFragment (Item itemModel){
        this();
        this.itemModel=itemModel;
        isUpdating=false;
        MainActivity.itemDao.getItem(this, itemModel.getId());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView)MainActivity.appBar.findViewById(R.id.titleFrame)).setText(R.string.item_update_title);
        editTextId = view.findViewById(R.id.editTextId);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        editTextContenedor = view.findViewById(R.id.editTextContenedor);
        buttonImagen = view.findViewById(R.id.buttonImagen);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirm);
                builder.setMessage(R.string.confirm_dialog);

                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateItem();
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
                SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
                ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
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
        ModalSelectContenedor modalSelectContenedor = new ModalSelectContenedor(new Contenedor(Long.valueOf(editTextContenedor.getText().toString().trim()),null,null,null,null,null));
        modalSelectContenedor.show(getParentFragmentManager(), "ContenedorSelect");
    }
    //finish crear modal

    //crear modal de seleccion de contenedor
    private void showListModal() {
        ModalViewContenedor modalViewContenedor = new ModalViewContenedor();
        modalViewContenedor.show(getParentFragmentManager(), "ContenedorView");
    }
    //finish crear modal

    //crear modal de scan qa de contenedor
    private void showScanModal() {
        ModalScanContenedor modalScanContenedor = new ModalScanContenedor();
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

    private void updateItem(){
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        if(validateItemForm()){
            Contenedor contenedor = (sIdContenedor.isEmpty())?null:new Contenedor(Long.valueOf(sIdContenedor), null, null, null, null, null);
            //itemModel=new Item(email,name,password);
            itemModel.setNombre(name);
            itemModel.setDescripcion(descripcion);
            itemModel.setContenedor(contenedor);
            isUpdating=true;
            MainActivity.itemDao.updateItem(this, itemModel);
        }
        else {
            //Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm() {
        String sId = editTextId.getText().toString().trim();
        String name = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();
        String sIdContenedor = editTextContenedor.getText().toString().trim();

        boolean result = true;
        if(sId.isEmpty()) {
            editTextId.setError(getString(R.string.id) + " " + getString(R.string.required_dialog));
            result = false;
        }
        if(!sId.isEmpty()){
            try{
                Long.valueOf(sId);
            }catch (NumberFormatException e) {
                editTextId.setError(getString(R.string.container)+ " " + getString(R.string.notnumeric_dialog));
                result = false;
            }
        }
        if(!sId.equals(itemModel.getId().toString())) {
            editTextId.setError(getString(R.string.id) + " " + "introducido no concuerda con el original");
            result = false;
        }
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
        itemModel = item;
        editTextId.setText(itemModel.getId().toString());
        editTextNombre.setText(itemModel.getNombre());
        editTextDescripcion.setText(itemModel.getDescripcion());
        if(itemModel.getContenedor()!=null) {
            editTextContenedor.setText(itemModel.getContenedor().getId().toString());
        }
        if(isUpdating) {
            Toast.makeText(getContext(), R.string.item_updated_dialog, Toast.LENGTH_LONG).show();
            //TODO subir imagen
            if(buttonImagen.getContentDescription().equals("imagen modificada")){
                new ModalInsertItemImagenFragment(item, getActivity(), buttonImagen).insertItemImagen();
            }
            //fin
            SelectItemFragment selectItemFragment = new SelectItemFragment(itemModel);
            ((MainActivity) getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
        }
        else if (!isUpdating) {
            //recuperar imagen
            new SelectItemImagenFragment(itemModel, this.getActivity(), buttonImagen).getItemImagen();
            ((MainActivity)getActivity()).changeFragment(R.id.LinearLayoutContenedorDeFragment, this);
        }
        isUpdating=false;
    }

    @Override
    public void callbackDataAcessError(Item item) {
        Toast.makeText(getContext(), R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}