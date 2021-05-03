package com.acme.dummyservice.interfaces;

import java.net.SocketException;
import java.util.Enumeration;

public interface NetworkUtils {
    public Enumeration retrieveNetworkInterfaces() throws SocketException;
}
