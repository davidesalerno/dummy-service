package com.acme.dummyservice.service;

import com.acme.dummyservice.interfaces.NetworkUtils;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class NetworkService {

    private final NetworkUtils netUtils;

    public NetworkService(NetworkUtils networkUtils){
        this.netUtils = networkUtils;
    }

    public List<String> getIPv4Addresses() throws SocketException {
        List<String> ipAddresses = new ArrayList<>();
        Enumeration e = netUtils.retrieveNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if(i instanceof Inet4Address){
                    ipAddresses.add(i.getHostAddress());
                }
            }
        }
        return ipAddresses;
    }
}
