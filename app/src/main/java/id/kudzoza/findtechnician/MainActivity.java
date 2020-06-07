package id.kudzoza.findtechnician;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.kudzoza.findtechnician.adapter.TypeAdapter;
import id.kudzoza.findtechnician.model.TypeModel;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<TypeModel> typeList = new ArrayList<>();
    TypeAdapter typeAdapter;
    ProgressBar loader;
    private String collectionPath = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize View
        RecyclerView recyclerView = findViewById(R.id.recycler);
        FloatingActionButton button = findViewById(R.id.btn);
        loader = findViewById(R.id.loader);

        // Initialize RecyclerView
        typeAdapter = new TypeAdapter(getApplicationContext(), typeList);
        recyclerView.setAdapter(typeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Read Data
        readData(collectionPath);

        // Listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("Inserted");
                loader.setVisibility(View.VISIBLE);

                Map<String, Object> type = new HashMap<>();
                type.put("name", "Teknisi AC");
                type.put("description", "Teknisi Service AC (Air Conditioner) Keliling");
                type.put("price_from", 50000);
                type.put("price_to", 100000);

                insertToDb(type);
            }
        });
    }

    private void insertToDb(final Map document) {
        typeList.clear();
        typeAdapter.notifyDataSetChanged();
        db.collection("type")
                .add(document)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        showMessage(document.toString());
                        readData(collectionPath);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage(e.getMessage());
                    }
                });
    }

    private void readData(String path) {
        db.collection(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                typeList.add(new TypeModel(
                                        (String) document.get("name"),
                                        (String) document.get("description"),
                                        document.getDouble("price_from").intValue(),
                                        document.getDouble("price_to").intValue()
                                ));
                            }
                            typeAdapter.notifyDataSetChanged();
                            loader.setVisibility(View.GONE);
                        } else {
                            showMessage(task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showMessage(e.getMessage());
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Log.d("Message : ", message);
    }
}
