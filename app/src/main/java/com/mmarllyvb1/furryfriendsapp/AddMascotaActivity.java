package com.mmarllyvb1.furryfriendsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmarllyvb1.furryfriendsapp.Modelo.Mascota;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddMascotaActivity extends AppCompatActivity {

    CircleImageView circeImageBack;

    private FirebaseFirestore dbI;
    private CollectionReference indexCol;
    private EditText txtNombre, txtCualidad, txtEdad, txtImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mascota);
        dbI=FirebaseFirestore.getInstance();
        indexCol=dbI.collection(DataInfo.MASCOTAS_REF);
        setup();

        circeImageBack=findViewById(R.id.circleImageBack);

        circeImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

    }

    //Binding componentes visuales
    public void setup(){
        txtNombre=findViewById(R.id.txtNombre);
        txtCualidad=findViewById(R.id.txtCualidad);
        txtEdad=findViewById(R.id.txtEdad);
        txtImagen=findViewById(R.id.txtImagen);
    }


    public void guardar(View view){
        String nombre = txtNombre.getText().toString();
        String cualidad = txtCualidad.getText().toString();
        String edad = txtEdad.getText().toString();
        String imagen = txtImagen.getText().toString();

        //Validaciones
        if (TextUtils.isEmpty(nombre)){
            txtNombre.setError("Nombre es obligatorio");
            return;
        }
        if(TextUtils.isEmpty(cualidad)){
            txtCualidad.setError("Cualidad es obligatoria");
            return;
        }
        if (TextUtils.isEmpty(edad)){
            txtEdad.setError("Edad es obligatoria");
            return;
        }
        if (TextUtils.isEmpty(imagen)){
            txtImagen.setError("Imagen es obligatoria");
            return;
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setCualidad(cualidad);
        mascota.setEdad(Integer.parseInt(edad));
        mascota.setImagen(imagen);

        indexCol.add(mascota)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMascotaActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


//    private void limpiarCajas(){
//        txtNombre.setText("");
//        txtCualidad.setText("");
//        txtEdad.setText("");
//        txtImagen.setText("");
//    }


}