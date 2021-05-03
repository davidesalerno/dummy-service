package com.acme.dummyservice.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ResourceUtil {
    public static String getResource(String path) throws IOException {
        try (InputStream inputStream = ResourceUtil.class.getResourceAsStream(path);
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())
        ) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}