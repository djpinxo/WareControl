package net.ddns.djpinxo.warecontrol;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;


import android.widget.Button;

import net.ddns.djpinxo.warecontrol.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //TODO revisar si esta logado mandar a un controller o a otro
        if (false){
            setContentView(R.layout.login_screen);

            Button loginButton = (Button) findViewById(R.id.login_button);
            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.login_button);

                }
            });
            Button regisButton = (Button) findViewById(R.id.registrer_button);
            regisButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("BUTTONS", "User tapped the Supabutton"+R.id.registrer_button);
                }
            });
        }
        else {
            Log.println(Log.INFO, this.getPackageName(), "usuario logado");
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            LoginFragment loginFragment=new LoginFragment();

            transaction.add(R.id.LinearLayoutContenedorDeFragment, loginFragment);
            transaction.commit();
        }



    }


}