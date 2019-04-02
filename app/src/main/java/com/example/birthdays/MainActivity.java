package com.example.birthdays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.birthdays.adapters.UserAdapter;
import com.example.birthdays.pojo.User;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    public static final String TAG = "test";
    private String name, lastName, gender;
    private int day, month, year;

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        users = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        recyclerViewUsers = findViewById(R.id.recyclerView);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter();

        users.clear();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Map<String, Object> userMap = documentSnapshot.getData();

                        name = userMap.get("name").toString();
                        lastName = userMap.get("lastName").toString();
                        gender = userMap.get("gender").toString();
                        day = Integer.parseInt(userMap.get("day").toString());
                        month = Integer.parseInt(userMap.get("month").toString());
                        year = Integer.parseInt(userMap.get("year").toString());

                        users.add(new User(name, lastName, gender, day, month, year));
                    }
                    userAdapter.setUsers(users);
                    recyclerViewUsers.setAdapter(userAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        Map<String, Object> user = new HashMap<>();
//        user.put("name", "Дени121с");
//        user.put("lastName", "Колесников");
//        user.put("gender", "male");
//        user.put("day", 21);
//        user.put("month", 8);
//        user.put("year", 1993);


//        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(getApplicationContext(), "Пользователь добавлен.", Toast.LENGTH_SHORT).show();
////                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Ошибка!" + e.getMessage(), Toast.LENGTH_SHORT).show();
////                Log.w(TAG, "Error adding document", e);
//            }
//        });
//
//
//        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                QuerySnapshot querySnapshot = task.getResult();
//                if (querySnapshot == null) return;
//
//                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
//                    Map<String, Object> user = documentSnapshot.getData();
//                    Log.i(TAG, user.get("name").toString());
//                    Log.i(TAG, user.get("lastName").toString());
//                    Log.i(TAG, user.get("day").toString());
//                    Log.i(TAG, user.get("month").toString());
//                    Log.i(TAG, user.get("year").toString());
//                    Log.i(TAG, user.get("gender").toString());
//
//                }
//            }
//        });


    }

    public void onClickAddUser(View view) {
        Intent intent = new Intent(MainActivity.this, AddUser.class);
        startActivity(intent);
    }


}
