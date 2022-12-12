package com.mmarllyvb1.furryfriendsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    //Declarar variables
    Button btnRegister;
    EditText nombres, apellidos, email, celular, documento, password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    CircleImageView circeImageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        circeImageBack=findViewById(R.id.circleImageBack);

        circeImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        nombres= findViewById(R.id.txtNombres);
        apellidos=findViewById(R.id.txtApellidos);
        email=findViewById(R.id.txtEmail);
        celular=findViewById(R.id.txtCelular);
        documento=findViewById(R.id.txtDocumento);
        password=findViewById(R.id.txtContrasena);
        btnRegister=findViewById(R.id.btnRegistrate);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUser=nombres.getText().toString().trim();
                String apelUser=apellidos.getText().toString().trim();
                String emailUser=email.getText().toString().trim();
                String celUser=celular.getText().toString().trim();
                String docUser=documento.getText().toString().trim();
                String passUser=password.getText().toString().trim();

                if (nomUser.isEmpty()||apelUser.isEmpty()||emailUser.isEmpty()||
                        celUser.isEmpty() ||docUser.isEmpty()||passUser.isEmpty()){
                    Toast.makeText(RegisterActivity.this,
                            "Complete los datos", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(nomUser, apelUser, emailUser, celUser, docUser, passUser);
                }
            }
        });
    }

    private void registerUser(String nomUser, String apelUser, String emailUser, String celUser,
                              String docUser, String passUser) {
    firebaseAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            String id=firebaseAuth.getCurrentUser().getUid();
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("nombre", nomUser);
            map.put("apellidos", apelUser);
            map.put("email", emailUser);
            map.put("celular", celUser);
            map.put("documento", docUser);
            map.put("password", passUser);

            firebaseFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    finish();
                    startActivity(new Intent(RegisterActivity.this, IndexActivity.class));
                    Toast.makeText(RegisterActivity.this,
                            "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this,
                            "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            })
            ;
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(RegisterActivity.this,
                    "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    })
    ;
    }
}