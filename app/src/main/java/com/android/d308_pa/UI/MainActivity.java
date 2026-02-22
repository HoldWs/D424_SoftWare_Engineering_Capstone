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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
                if ((username.equals("username")) && (password.equals("Password1!"))) {
                    Intent intent = new Intent(MainActivity.this, VacationList.class);
                    startActivity(intent);}
                else if (!validatePassword(password)) {
                    Toast.makeText(MainActivity.this, "Your password needs to be at least 8 to 20 characters long and needs one at least one capital letter, one lowercase letter, a number and a special character!", Toast.LENGTH_SHORT).show();
                }
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
    public boolean validatePassword(String password) {

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}