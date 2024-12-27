package com.stoyanvuchev.serializationexample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Sender {

    public static void sendPackets(
            OutputStream outputStream,
            List<byte[]> packets
    ) throws IOException {

        for (int i = 0; i < packets.size(); i++) {

            // Get the packet.
            byte[] packet = packets.get(i);

            // Create a header and write the metadata.
            ByteArrayOutputStream header = new ByteArrayOutputStream();
            header.write(intToBytes(packet.length)); // Packet size (4 bytes).
            header.write(intToBytes(i + 1)); // Packet sequence number (4 bytes).
            header.write(intToBytes(packets.size())); // Total number of packets (4 bytes).

            // Send the header, followed by the packet data.
            outputStream.write(header.toByteArray());
            outputStream.write(packet);

        }

        // When the transmission is complete, flush the output stream.
        outputStream.flush();

    }

    // A helper method to convert an integer into a 4-byte array.
    private static byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

}
