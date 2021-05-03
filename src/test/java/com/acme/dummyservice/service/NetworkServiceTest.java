package com.acme.dummyservice.service;

import com.acme.dummyservice.interfaces.NetworkUtils;
import com.acme.dummyservice.utils.ProxyNetwork;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NetworkServiceTest {

    NetworkUtils mockedNetUtils = mock(ProxyNetwork.class);
    NetworkInterface mockedInterface = mock(NetworkInterface.class);
    InetAddress mockedInet4Address = mock(Inet4Address.class);
    NetworkService serviceUnderTest = new NetworkService(mockedNetUtils);

    @SneakyThrows
    @Test
    public void givenAHostShouldRetrieveAllTheIPv4AddressesAvailable(){
        when(mockedInet4Address.getHostAddress()).thenReturn("127.0.0.1");
        Enumeration<InetAddress> addressList = new Vector<>(new HashSet<>(Arrays.asList(mockedInet4Address))).elements();
        when(mockedInterface.getInetAddresses()).thenReturn(addressList);
        Enumeration<NetworkInterface> networkInterfacesList = new Vector<>(new HashSet<>(Arrays.asList(mockedInterface))).elements();
        when(mockedNetUtils.retrieveNetworkInterfaces()).thenReturn(networkInterfacesList);
        List<String> result = serviceUnderTest.getIPv4Addresses();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo("127.0.0.1");
    }

}