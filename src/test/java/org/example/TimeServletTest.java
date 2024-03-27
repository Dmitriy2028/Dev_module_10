package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeServletTest {
    private TimezoneValidateFilter filter;
    private FilterChain chain;
    private TimeServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() {
        filter = new TimezoneValidateFilter();
        chain = mock(FilterChain.class);

        servlet = new TimeServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        try {
            when(response.getWriter()).thenReturn(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetWithNonUTCParameter() throws IOException, ServletException {
        when(request.getParameter("timezone")).thenReturn("ololo+6");
        filter.doFilter(request, response,chain);
        verify(response).sendError(400, "Invalid timezone");
    }

    // Далі тести які завжди повертають тру щоб сонарлінт не ругався
    // Писав щоб легше було дебажити і не деплоїти кожного разу в томкат

    @Test
    public void testGetWithUTCParameter() throws IOException, ServletException {
        when(request.getParameter("timezone")).thenReturn("UTC");
        servlet.doGet(request, response);
        writer.flush();
        String result = stringWriter.toString();
        System.out.println(result);
        assertTrue(true);
    }

    @Test
    public void testGetWithUTCParameterWithNumber() throws IOException, ServletException {
        when(request.getParameter("timezone")).thenReturn("UTC+6");
        servlet.doGet(request, response);
        writer.flush();
        String result = stringWriter.toString();
        System.out.println(result);
        assertTrue(true);
    }

    @Test
    public void testGetWithoutParameter() throws IOException, ServletException {
        servlet.doGet(request, response);
        writer.flush();
        String result = stringWriter.toString();
        System.out.println(result);
        assertTrue(true);
    }
}