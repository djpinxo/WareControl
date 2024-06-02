package net.ddns.djpinxo.warecontrol.ui.item;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import net.ddns.djpinxo.warecontrol.MainActivity;
import net.ddns.djpinxo.warecontrol.R;
import net.ddns.djpinxo.warecontrol.data.model.Item;
import net.ddns.djpinxo.warecontrol.ui.FragmentCallback;
import net.ddns.djpinxo.warecontrol.ui.contenedor.SelectContenedorFragment;

import java.util.concurrent.ExecutionException;

public class ModalInsertItemImagenFragment extends DialogFragment implements FragmentCallback<String> {

    //creado por mantener una estructura aun que no sea fragment

    private Activity activity;
    private ImageView imageView;
    private PreviewView previewView;

    private Item itemModel;

    public ModalInsertItemImagenFragment(){
        super();
    }
    public ModalInsertItemImagenFragment(Item itemModel, Activity activity, ImageView imageView){
        this();
        this.itemModel=itemModel;
        this.activity = activity;
        this.imageView = imageView;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modal_camera, container, false);
        previewView = view.findViewById(R.id.previewView);


        startCamera();

        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> dismiss());
        //TODO boton tomar foto
        ImageButton buttonCapture = view.findViewById(R.id.buttonCapture);
        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(previewView.getBitmap());
                imageView.setContentDescription("imagen modificada");
                dismiss();

            }
        });

        return view;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        /*BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build();
        barcodeScanner = BarcodeScanning.getClient(options);*/

        //imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()), this::processImageProxy);

        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

/*
    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImageProxy(ImageProxy imageProxy) {
        InputImage image = InputImage.fromMediaImage(imageProxy.getImage(), imageProxy.getImageInfo().getRotationDegrees());

        barcodeScanner.process(image)
                .addOnSuccessListener(barcodes -> {
                    for (Barcode barcode : barcodes) {
                        String rawValue = barcode.getRawValue();
                        if (rawValue != null) {
                            sendResultToActivity(rawValue);
                            dismiss();
                            break;
                        }
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace)
                .addOnCompleteListener(task -> imageProxy.close());
    }

    private void sendResultToActivity(String qrData) {
        boolean isNumber=false;
        try{
            Long.valueOf(qrData);
            isNumber=true;
        }catch (NumberFormatException e) {
            //editTextContenedor.setError(getString(R.string.dadcontainer)+ " " + getString(R.string.notnumeric_dialog));
            Toast.makeText(getContext(), R.string.notnumeric_dialog, Toast.LENGTH_LONG).show();
            isNumber=false;
        }

        if(isNumber && getActivity().findViewById(R.id.editTextContenedor)!=null){
            ((EditText)getActivity().findViewById(R.id.editTextContenedor)).setText(qrData);
        }
        if(isNumber && getActivity().findViewById(R.id.editTextContenedorPadre)!=null){
            ((EditText)getActivity().findViewById(R.id.editTextContenedorPadre)).setText(qrData);
        }
    }
*/
    public void insertItemImagen(){
        if(validateItemForm()){
            MainActivity.itemDao.insertItemImagen(this, itemModel.getId(), imageView);
        }
        else {
            Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateItemForm() {
        return !itemModel.getId().toString().isEmpty();
    }

    @Override
    public void callbackDataAcessSuccess(String string) {
        //ImageButton imageButton = activity.findViewById(R.id.imageView);
        SelectItemFragment selectItemFragment=new SelectItemFragment(itemModel);
        ((MainActivity)activity).changeFragment(R.id.LinearLayoutContenedorDeFragment, selectItemFragment);
    }

    @Override
    public void callbackDataAcessError(String string) {
        Toast.makeText(activity, R.string.error_dialog, Toast.LENGTH_LONG).show();
    }
}