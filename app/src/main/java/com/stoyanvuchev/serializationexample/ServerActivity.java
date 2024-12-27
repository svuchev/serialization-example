package com.stoyanvuchev.serializationexample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.stoyanvuchev.serializationexample.util.IPAddressUtil;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerActivity extends AppCompatActivity {

    private final static String TAG = "ServerActivityTag";
    private String ipAddress;
    private static final int PORT = 8383;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_server);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Start the server on a new thread.
        new Thread(this::startServer).start();

    }

    @SuppressLint("SetTextI18n")
    private void startServer() {

        ipAddress = IPAddressUtil.getLocalIPv4Address();

        // If there's no IP Address, throw a NullPointerException and notify the user.
        if (ipAddress == null) {
            String exceptionMsg = "Server error: Cannot obtain an IP Address for the server. Exiting.";
            runOnUiThread(() -> Toast.makeText(this, exceptionMsg, Toast.LENGTH_LONG).show());
            throw new NullPointerException(exceptionMsg);
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            Log.d(TAG, "Server started. Waiting for a client ...");
            runOnUiThread(() -> {
                TextView textView = findViewById(R.id.serverStatsTextView);
                textView.setText("Server is running. \n IP: " + ipAddress + "\n Port: " + PORT);
            });

            while (true) {

                Socket clientSocket = serverSocket.accept();
                Log.d(TAG, "Client connected.");
                runOnUiThread(() -> Toast.makeText(this, "Client connected.", Toast.LENGTH_SHORT).show());

                try (OutputStream outputStream = clientSocket.getOutputStream()) {

                    // Dummy data
                    byte[] data = new byte[127750];
                    for (int i = 0; i < data.length; i++) {
                        data[i] = (byte) (i % 256);
                    }

                    // Packetize the data.
                    int packetSize = 100000;
                    List<byte[]> packets = AdaptivePacketization.packetize(data, packetSize);

                    // Send packets.
                    Sender.sendPackets(outputStream, packets);
                    Log.d(TAG, "All packets sent.");
                    runOnUiThread(() -> Toast.makeText(this, "All packets sent.", Toast.LENGTH_SHORT).show());

                } catch (Exception e) {
                    Log.e(TAG, ": Error: " + e.getMessage());
                }

                Thread.sleep(10);

            }

        } catch (Exception e) {
            Log.e(TAG, ": Server Error: " + e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ipAddress = null;
    }

}