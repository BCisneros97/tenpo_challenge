package com.example.tenpo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter implements Filter {
  private final int MAX_REQUESTS_PER_MINUTE = 3;
  private final Map<String, LinkedList<Long>> requestLog = new ConcurrentHashMap<>();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String clientIp = httpRequest.getRemoteAddr();
    synchronized (requestLog) {
      requestLog.computeIfAbsent(clientIp, k -> new LinkedList<>());
      LinkedList<Long> clientRequests = requestLog.get(clientIp);
      while (!clientRequests.isEmpty() && clientRequests.peekFirst() < System.currentTimeMillis() - 60000) {
        clientRequests.removeFirst();
      }
      if (clientRequests.size() >= MAX_REQUESTS_PER_MINUTE) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        httpResponse.getWriter().write("Too many requests");
        return;
      }
      clientRequests.addLast(System.currentTimeMillis());
    }
    chain.doFilter(request, response);
  }
}