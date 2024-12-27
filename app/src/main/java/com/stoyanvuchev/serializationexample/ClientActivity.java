package com.stoyanvuchev.serializationexample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity {

    private final static int PORT = 8383;
    private String ipAddress;
    private final static String TAG = "ClientActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpClient();

    }

    private void setUpClient() {

        // Obtain the IP Address from the intent extras.
        ipAddress = getIntent().getStringExtra("IP_ADDRESS");

        // If there's no IP Address, throw a NullPointerException and notify the user.
        if (ipAddress == null) {
            String exceptionMsg = "Client error: Cannot obtain an IP Address for the server. Exiting.";
            runOnUiThread(() -> Toast.makeText(this, exceptionMsg, Toast.LENGTH_LONG).show());
            throw new NullPointerException(exceptionMsg);
        }

        // Finally start the client.
        new Thread(this::startClient).start();

    }

    private void startClient() {
        try (
                Socket socket = new Socket(ipAddress, PORT);
                InputStream inputStream = socket.getInputStream()
        ) {

            Log.d(TAG, "Connected to server.");
            runOnUiThread(() -> Toast.makeText(this, "Connected to server.", Toast.LENGTH_SHORT).show());

            // Receive packets.
            byte[] receivedData = Receiver.receivePackets(inputStream);

            String receivedDataMsg = "Data received. Total size: " + receivedData.length + " bytes.";
            Log.d(TAG, receivedDataMsg);
            runOnUiThread(() -> Toast.makeText(this, receivedDataMsg, Toast.LENGTH_SHORT).show());

        } catch (Exception e) {
            Log.e(TAG, ": Client Error: " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipAddress = null;
    }

}