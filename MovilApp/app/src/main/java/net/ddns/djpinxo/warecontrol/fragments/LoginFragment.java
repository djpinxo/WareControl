package net.ddns.djpinxo.warecontrol.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.ddns.djpinxo.warecontrol.R;

import java.net.MalformedURLException;
import java.net.URL;

public class LoginFragment extends Fragment {

    EditText etURL;
    Button btnCargarPagina;
    WebView webView1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.fragment_fragment_navegador, container, false);
        etURL=(EditText)v.findViewById(R.id.etURL);
        btnCargarPagina=(Button)v.findViewById(R.id.btnCargarPagina);
        webView1=(WebView)v.findViewById(R.id.webView1);

        btnCargarPagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=etURL.getText().toString();
                if(!TextUtils.isEmpty(url)) {
                    webView1.setWebViewClient(new WebViewClient()); //Para que no abra un navegador externo a la aplicacion
                    webView1.getSettings().setJavaScriptEnabled(true); //Para que procese codigo JavaScript
                    webView1.loadUrl("https://" + url);
                }
                else
                    Toast.makeText(getContext(), "Debe indicar una url", Toast.LENGTH_SHORT).show();
            }
        });



        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment_navegador, container, false);
        return v;
    }
}
