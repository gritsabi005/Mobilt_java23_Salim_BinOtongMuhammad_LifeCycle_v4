package com.example.javakotlin;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    Button homeButton, registerButton;
    EditText firstName, lastName, emailAddress, birthday, password;
    CheckBox subscriptionBox;
    Firebase db;
    String firstname, lastname, emailadress, birthdayy, passwordd;
    Integer birthdayyy;
    boolean subscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = Firebase.INSTANCE;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener((e) -> {
            Intent homePage = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(homePage);
            write();
        });

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        emailAddress = findViewById(R.id.emailEditText);
        birthday = findViewById(R.id.birthdayEditText);
        password = findViewById(R.id.passwordEditText);
        subscriptionBox = findViewById(R.id.checkBox);


        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener((e) ->{

             firstname = firstName.getText().toString();
             lastname = lastName.getText().toString();
             emailadress = emailAddress.getText().toString();
             birthdayy = birthday.getText().toString();
             birthdayyy = Integer.parseInt(birthdayy);
             passwordd = password.getText().toString();
             subscribe = subscriptionBox.isChecked();

            String subscriptionOpt;
            if (subscribe) {
                subscriptionOpt = "subscribe";
            } else {
                subscriptionOpt = "not subscribe";
            }

            if (firstname.isEmpty() || lastname.isEmpty() || emailadress.isEmpty() || birthdayy.isEmpty() || passwordd.isEmpty()){
                Toast.makeText(this, "Registration fails. Fill in all the fields", Toast.LENGTH_SHORT).show();
            }
            else if (!emailValid(emailadress)) {
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
            } else if (!passwordValid(passwordd)) {
                Toast.makeText(this, "Password must contain at least one number.", Toast.LENGTH_SHORT).show();
            } else {
                write();
                System.out.println("New User created with credentials: " + firstname + " " + lastname + ", using email address as log in id: " + emailadress + ", and password chosen: " + passwordd + ". The user opt to " + subscriptionOpt + " to the news. ");
                Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
                emptyRegistration();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.birthdayText), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean emailValid(String userEmail){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
    }

    //helping to authenticate the Password chosen
    private boolean passwordValid(String userPassword){
        return userPassword.matches(".*\\d.*");
    }

    public void emptyRegistration(){
        firstName.setText("");
        lastName.setText("");
        emailAddress.setText("");
        birthday.setText("");
        password.setText("");
        subscriptionBox.setChecked(false);
    }

    public void write() {
        // Initialize Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a HashMap
        Map<String, Object> user = new HashMap<>();
        user.put("first", firstname);
        user.put("last", lastname);
        user.put("email", emailadress);
        user.put("birthday", birthdayyy);
        user.put("password", passwordd);
        user.put("subscription", subscribe);

        //what do you mean with authentication

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId())
                )
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error adding document", e)
                );
    }
}