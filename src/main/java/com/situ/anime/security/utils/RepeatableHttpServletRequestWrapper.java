package com.situ.anime.security.utils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.*;

/**
 * 用来解决多个过滤器都需要读取request请求的问题
 * @author liangyunfei
 */
//public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {
////    private final byte[] requestBodyBytes;
////
////    public RepeatableHttpServletRequestWrapper(HttpServletRequest request) throws IOException, IOException {
////        super(request);
////        // 读取原始请求体
////        try (InputStream inputStream = request.getInputStream()) {
////            requestBodyBytes = inputStream.readAllBytes();
////        }
////    }
////
////    @Override
////    public ServletInputStream getInputStream() throws IOException {
////        // 返回一个可重复读取的ServletInputStream
////        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBodyBytes);
////        return new ServletInputStream() {
////            @Override
////            public boolean isFinished() {
////                return false;
////            }
////
////            @Override
////            public boolean isReady() {
////                return true;
////            }
////
////            @Override
////            public void setReadListener(ReadListener listener) {
////                // 这里是空，因为不需要实现异步操作
////            }
////
////            @Override
////            public int read() throws IOException {
////                return bais.read();
////            }
////        };
////    }
////
////    // 覆盖getReader方法
////    @Override
////    public BufferedReader getReader() throws IOException {
////        return new BufferedReader(new InputStreamReader(getInputStream()));
////    }
//}
