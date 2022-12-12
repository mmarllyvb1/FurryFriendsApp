package com.mmarllyvb1.furryfriendsapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmarllyvb1.furryfriendsapp.Modelo.Mascota;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMascotaActivity extends AppCompatActivity {

    CircleImageView circeImageBack;

    private FirebaseFirestore dbI;
    private CollectionReference indexCol;
    private EditText txtNombre, txtCualidad, txtEdad, txtImagen;
    private String idMascota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mascota);
        circeImageBack=findViewById(R.id.circleImageBack);

        circeImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });
        idMascota=getIntent().getStringExtra("idMascota");
        dbI=FirebaseFirestore.getInstance();
        indexCol=dbI.collection(DataInfo.MASCOTAS_REF);
        setup();

    }

    //Binding componentes visuales
    public void setup(){
        txtNombre=findViewById(R.id.txtNombre);
        txtCualidad=findViewById(R.id.txtCualidad);
        txtEdad=findViewById(R.id.txtEdad);
        txtImagen=findViewById(R.id.txtImagen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarDatos();
    }

    private void cargarDatos(){
        indexCol.document(idMascota).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documento) {
                        if(documento.exists()){
                            Mascota mascota = documento.toObject(Mascota.class);
                            mostrarDatos(mascota);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditMascotaActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void mostrarDatos(Mascota mascota){
        txtNombre.setText(mascota.getNombre());
        txtCualidad.setText(mascota.getCualidad());
        txtEdad.setText(String.valueOf(mascota.getEdad()));
        txtImagen.setText(mascota.getImagen());

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
        if(edad==null||edad.length()==0){
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
        mascota.setId(idMascota);
        mascota.setNombre(nombre);
        mascota.setCualidad(cualidad);
        mascota.setEdad(Integer.parseInt(edad));
        mascota.setImagen(imagen);

        indexCol.document(idMascota).set(mascota)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditMascotaActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void eliminar(View view){
        indexCol.document(idMascota).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditMascotaActivity.this,
                                "Error="+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}