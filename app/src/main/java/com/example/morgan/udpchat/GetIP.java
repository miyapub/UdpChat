package com.example.morgan.udpchat;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by morgan on 2017/7/30.
 */

public class GetIP {
    public String getLocalIP(){
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;
    }
    public String[] getAllIp(){
        String ip=getLocalIP();//获得ip
        String ip_network_segment="";
        String[] sourceIP = ip.split("\\.");//ip分段数组
        for (int i = 0; i < sourceIP.length-1; i++) {
            ip_network_segment+=sourceIP[i]+".";
        }
        String[] ipArray=new String[254];
        for (int i = 0; i < ipArray.length; i++) {
            ipArray[i]=ip_network_segment+i;
        }
        return ipArray;
    }
}
