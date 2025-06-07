package com.github.frog.features.resources;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ResourceTestData {

    public static MultipartFile mockMultipartFile() {
        return new MockMultipartFile("file", "file.txt", "text/plain", "hello world".getBytes());
    }
}
