package com.stoyanvuchev.serializationexample;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlaneSerializer {

    private final static String TAG = "PlaneSerializerTag";

    public void serialize(Plane plane, FileOutputStream file) {

        try {

            //Saving of object in a file
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(plane);

            out.close();
            file.close();

            System.out.println(TAG + ": The Plane was serialized successfully!");

        } catch (Exception e) {

            Log.e(TAG, "Cannot serialize Plane: " + e.getMessage());

        }

    }

    public Plane deserialize(FileInputStream file) {

        try {

            Plane plane;

            // Reading the object from a file
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            plane = (Plane) in.readObject();

            in.close();
            file.close();

            System.out.println(TAG + ": The Plane was deserialized successfully! " + plane);

            return plane;

        } catch (Exception e) {

            Log.e(TAG, "Cannot serialize Plane: " + e.getMessage());
            return null;

        }

    }

}
