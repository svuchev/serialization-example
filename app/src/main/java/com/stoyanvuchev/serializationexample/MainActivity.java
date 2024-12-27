package com.stoyanvuchev.serializationexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startServerButton, joinServerButton;

        startServerButton = findViewById(R.id.startServerButton);
        startServerButton.setOnClickListener(v -> startServer());

        joinServerButton = findViewById(R.id.joinServerButton);
        joinServerButton.setOnClickListener(v -> joinServer());

    }

    private void startServer() {
        Intent startServerIntent = new Intent(this, ServerActivity.class);
        startActivity(startServerIntent);
    }

    private void joinServer() {

        EditText editText = findViewById(R.id.ipAddressEditText);
        String ipAddress = editText.getText().toString();

        if (!ipAddress.isBlank()) {
            Intent joinServerIntent = new Intent(this, ClientActivity.class);
            joinServerIntent.putExtra("IP_ADDRESS", ipAddress);
            startActivity(joinServerIntent);
        }

    }

}