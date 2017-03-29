package com.goyito.escom.verimovil01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goyito.escom.verimovil01.R;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.Email;

/**
 * Created by goyito on 10/03/17.
 */

public class ActividadRecuperar extends AppCompatActivity implements Validator.ValidationListener {
    private View vista;
    private Validator validator;

    @Required(order = 1, message = "Su email es necesario")
    @Email(order = 2, message = "Debe introducir un email valido")
    private EditText correo;
    String Scorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_recuperar);
        vista = findViewById(R.id.actividad_recuperar);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Button botonRecuperar = (Button) findViewById(R.id.recuperar);
        botonRecuperar.setOnClickListener(RecuperarClickListener);
    }

    private View.OnClickListener RecuperarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            correo = (EditText) findViewById(R.id.txtusuario);
            Scorreo = (String) correo.getText().toString();
            validator.validate();
        }
    };

    @Override
    public void preValidation() {

    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Se ha enviado un correo con su contraseña", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onFailure(View failedView, Rule failedRule) {
        Log.w("onFailure", "Mensage error " + failedRule.getFailureMessage());
        Log.w("onFailure", "failedView ID " + failedView.getId());
        Toast.makeText(this, failedRule.getFailureMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationCancelled() { }
}
