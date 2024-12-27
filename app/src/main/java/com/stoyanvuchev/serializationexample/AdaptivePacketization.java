package com.stoyanvuchev.serializationexample;

import java.util.ArrayList;
import java.util.List;

public class AdaptivePacketization {

    public static List<byte[]> packetize(byte[] data, int packetSize) {

        List<byte[]> packets = new ArrayList<>();
        int totalSize = data.length;
        int offset = 0;

        // Loop until all the data is packetized.
        while (offset < totalSize) {

            // Determine the size of the current packet.
            int currentPacketSize = Math.min(packetSize, totalSize - offset);

            // Create a new packet and copy the corresponding data.
            byte[] packet = new byte[currentPacketSize];
            System.arraycopy(data, offset, packet, 0, currentPacketSize);

            // Add the packet to the packets list.
            packets.add(packet);

            // Move the offset forward.
            offset += currentPacketSize;

        }

        // Finally return the packets.
        return packets;

    }

}