package com.acme.dummyservice.utils;

import com.acme.dummyservice.interfaces.NetworkUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ProxyNetwork implements NetworkUtils {
    @Override
    public Enumeration retrieveNetworkInterfaces() throws SocketException {
        return NetworkInterface.getNetworkInterfaces();
    }
}
