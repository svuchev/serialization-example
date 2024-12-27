package com.stoyanvuchev.serializationexample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Plane plane;
    private Plane deserializedPlane;

    private PlaneSerializer serializer;
    private Thread ioThread;

    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;

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

        // Initialize the Plane.
        // initializePlane();

        // Attempt to serialize and then to deserialize the Plane.
        // serializePlane();

        packetize();

    }

    private void packetize() {
        new Thread(() -> {
            try {

                // Dummy data
                byte[] data = new byte[127750];
                for (int i = 0; i < data.length; i++) {
                    data[i] = (byte) (i % 256);
                }

                // Packetize the data.
                int packetSize = 100000;
                List<byte[]> packets = AdaptivePacketization.packetize(data, packetSize);

                // Send the packets.
                fileOutputStream = openFileOutput("Plane.bin", Context.MODE_PRIVATE);
                Sender.sendPackets(fileOutputStream, packets);

                // Receive data
                fileInputStream = openFileInput("Plane.bin");
                byte[] receivedData = Receiver.receivePackets(fileInputStream);
                System.out.println(TAG + ": Received data size: " + receivedData.length);

            } catch (IOException | InterruptedException e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }).start();
    }

    private void initializePlane() {
        plane = new Plane(
                "Textron eAviation",
                "Pipistrel",
                "Sinus",
                2024,
                "LZ-PSE",
                2024,
                "Stoyan Vuchev",
                "One Way St., 8A, Altered Universe",
                "Chester County Airport, Coatesville, PA, US",
                "Chester County Airport, Coatesville, PA, US",
                "",
                false
        );
    }

    private void serializePlane() {
        ioThread = new Thread(() -> {
            try {

                String fileName = "Plane.bin";
                serializer = new PlaneSerializer();

                fileOutputStream = this.openFileOutput(fileName, Context.MODE_PRIVATE);
                serializer.serialize(plane, fileOutputStream);

                Thread.sleep(500);
                deserializePlane();

            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        });
        ioThread.start();
    }

    private void deserializePlane() {
        try {

            String fileName = "Plane.bin";

            fileInputStream = this.openFileInput(fileName);
            deserializedPlane = serializer.deserialize(fileInputStream);

            Thread.sleep(500);
            updateUI();

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    private void updateUI() {
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.planeTextView);
            textView.setText(deserializedPlane.toString());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            fileInputStream.close();
            fileInputStream = null;

            fileOutputStream.close();
            fileOutputStream = null;

            plane = null;
            deserializedPlane = null;
            serializer = null;

            ioThread.interrupt();
            ioThread = null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}