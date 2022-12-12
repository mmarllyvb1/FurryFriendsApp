package com.mmarllyvb1.furryfriendsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mmarllyvb1.furryfriendsapp.Adapters.Adaptador;
import com.mmarllyvb1.furryfriendsapp.Modelo.Mascota;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IndexActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;
    private CollectionReference mascotasCol;
    private ListView lista;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        db = FirebaseFirestore.getInstance();
        mascotasCol = db.collection(DataInfo.MASCOTAS_REF);
        Toast.makeText(this, "Inicializando Base de Datos", Toast.LENGTH_SHORT).show();
        lista = findViewById(R.id.lvAdopcion);
//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Mascota mascota = (Mascota) lista.getItemAtPosition(position);
////                Toast.makeText(IndexActivity.this,
////                        ""+mascota.getNombre(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(IndexActivity.this,
//                        EditMascotaActivity.class);
//                intent.putExtra("idMascota", mascota.getId());
//                        llamados.launch(intent);
//            }
//        });

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.iconhome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.iconadd:
                        startActivity(new Intent(getApplicationContext(),AddMascotaActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.iconhome:
                        return true;

                    case R.id.iconperfil:
                        startActivity(new Intent(getApplicationContext(),PerfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
//        btnLogout=findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Intent logout= new Intent(IndexActivity.this, MainActivity.class);
//                startActivity(logout);
//                finish();
//                Toast.makeText(IndexActivity.this,
//                        "Sesión cerrada", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarDatos();
    }

    //Encargado de hacer el callback de la coleccion que hicimos
    private void cargarDatos(){
        mascotasCol.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Mascota> mascotas = new ArrayList<>();
                            for (QueryDocumentSnapshot documento: task.getResult()) {
                                Mascota mascota = documento.toObject(Mascota.class);
                                mascotas.add(mascota);
                            }
                            mostrarDatos(mascotas);
                        } else {
                            Toast.makeText(IndexActivity.this,
                                    "Error en la carga", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mostrarDatos(ArrayList<Mascota> mascotas){
        Adaptador adapter = new Adaptador(IndexActivity.this, mascotas);
        lista.setAdapter(adapter);
    }

    ActivityResultLauncher<Intent> llamados = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()==RESULT_OK) {
                        cargarDatos();
                    }
                }
            }
    );

    public void logout(View view){
        Intent out = new Intent(IndexActivity.this, MainActivity.class);
        startActivity(out);
    }

}