package br.com.meuscontatos.principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
    }

    public void registrar(View view){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void autenticar(View view){

    }

}
