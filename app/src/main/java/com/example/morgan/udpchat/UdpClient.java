package com.example.morgan.udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by morgan on 2017/7/30.
 */

public class UdpClient {
    public void sendUdpMsg(String ip,int port,String msg) {
        DatagramSocket socket = null;
        InetAddress serverAddress = null;
        byte data[] = msg.getBytes();
        try {
            serverAddress = InetAddress.getByName(ip);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            socket = new DatagramSocket(port+1);
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
