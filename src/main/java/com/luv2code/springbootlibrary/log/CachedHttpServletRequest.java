package com.luv2code.springbootlibrary.log;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.Arrays;

public class CachedHttpServletRequest extends HttpServletRequestWrapper {
    private byte[] cachedPayload;

    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();

        this.cachedPayload = StreamUtils.copyToByteArray(requestInputStream);
        System.out.println("Cached Payload (Request body content): " + Arrays.toString(cachedPayload));
        System.out.println("Cached Payload: (Request bodycontent): " + new String(cachedPayload));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(this.cachedPayload);

    }

    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}
