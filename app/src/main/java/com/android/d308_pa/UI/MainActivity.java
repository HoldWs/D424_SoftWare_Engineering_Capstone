package com.android.d308_pa.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.d308_pa.R;


public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        EditText userNameInput = findViewById(R.id.username_input);
        EditText passwordInput = findViewById(R.id.password_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameInput.getText().toString();
                String password = passwordInput.getText().toString();
                Log.i("Test Credentials", "Username: " + username + " and Password: " + password);
                if ((username.isEmpty()) && (password.isEmpty())) {
                    Toast.makeText(MainActivity.this, "Please enter a username and password!", Toast.LENGTH_LONG).show();
                }
                else if ((username.equals("username")) && (password.equals("password"))) {
                    Intent intent = new Intent(MainActivity.this, VacationList.class);
                    startActivity(intent);                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid username and password!", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}