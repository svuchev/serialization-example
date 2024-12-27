package com.stoyanvuchev.serializationexample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Receiver {

    public static byte[] receivePackets(InputStream inputStream) throws IOException, InterruptedException {

        ByteArrayOutputStream dataBuffer = new ByteArrayOutputStream();
        byte[] headerBuffer = new byte[12]; // 4 bytes (packet size) + 4 bytes (sequence) + 4 bytes (total packets) = 12.

        while (true) {

            // Read the header.
            int headerBytesRead = inputStream.read(headerBuffer);
            if (headerBytesRead == -1) break; // There is no metadata, so we stop.

            if (headerBytesRead != headerBuffer.length) {
                throw new IOException("Incomplete or corrupted header received!");
            }

            // Extract the metadata from the header.
            int packetSize = bytesToInt(headerBuffer, 0);
            int sequenceNumber = bytesToInt(headerBuffer, 4);
            int totalPackets = bytesToInt(headerBuffer, 8);

            // Read the packet data.
            byte[] packetData = new byte[packetSize];
            int dataBytesRead = inputStream.read(packetData);
            if (dataBytesRead != packetSize) {
                throw new IOException("Incomplete or corrupted packet is received!");
            }

            // Append packet data to the buffer.
            dataBuffer.write(packetData);

            // Stop the loop if the last packet has been received.
            if (sequenceNumber == totalPackets) break;

            // Add a bit of an artificial delay.
            Thread.sleep(10);

        }

        // Finally return the packets.
        return dataBuffer.toByteArray();

    }

    // A helper method to convert a 4-byte array to an integer.
    private static int bytesToInt(byte[] bytes, int offset) {
        return (bytes[offset] & 0xFF) << 24 |
                (bytes[offset + 1] & 0xFF) << 16 |
                (bytes[offset + 2] & 0xFF) << 8 |
                (bytes[offset + 3] & 0xFF);
    }

}
