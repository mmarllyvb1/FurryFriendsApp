package com.mmarllyvb1.furryfriendsapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mmarllyvb1.furryfriendsapp.Adapters.Adaptador;
import com.mmarllyvb1.furryfriendsapp.Modelo.Mascota;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private FirebaseFirestore db;
    private CollectionReference mascotasCol;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db = FirebaseFirestore.getInstance();
        mascotasCol = db.collection(DataInfo.MASCOTAS_REF);
        Toast.makeText(this, "Inicializando Base de Datos", Toast.LENGTH_SHORT).show();
        lista = findViewById(R.id.lvMisMascotas);

        bottomNavigationView = findViewById(R.id.bottomNavigator);
        bottomNavigationView.setSelectedItemId(R.id.iconperfil);

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
                        startActivity(new Intent(getApplicationContext(),IndexActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.iconperfil:
                        return true;
                }

                return false;
            }
        });

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
                            Toast.makeText(PerfilActivity.this,
                                    "Error en la carga", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void mostrarDatos(ArrayList<Mascota> mascotas){
        Adaptador adapter = new Adaptador(PerfilActivity.this, mascotas);
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

    public void agregar(View view){
        Intent add = new Intent(PerfilActivity.this, AddMascotaActivity.class);
        startActivity(add);
    }

}