package com.arton.backend.infra.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class RequestServletWrapper extends HttpServletRequestWrapper {
    private final Logger log = LoggerFactory.getLogger("ELASTIC");

    private String requestData = null;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestServletWrapper(HttpServletRequest request) {
        super(request);

        try(Scanner s = new Scanner(request.getInputStream()).useDelimiter("\\A")){
            requestData = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestData.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            private ReadListener readListener = null;

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }
}
