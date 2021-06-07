package com.example.finalprojectpm.web.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class SessionAdminFilterTest {
    private HttpServletRequest httpServletRequest ;
    private HttpServletResponse httpServletResponse ;
    private HttpSession session ;
    private FilterChain filterChain ;
    @BeforeEach
    void init(){
        // create the objects to be mocked
        httpServletRequest = mock(HttpServletRequest.class);
        httpServletResponse = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        filterChain = mock(FilterChain.class);
    }
    @Test
    void doFilter() throws ServletException, IOException {
        // create the objects to be mocked
        when(httpServletRequest.getSession(false)).thenReturn(session);
        when(session.getAttribute("userType")).thenReturn("client");

        SessionAdminFilter sessionAdminFilter = new SessionAdminFilter();
        sessionAdminFilter.doFilter(httpServletRequest, httpServletResponse,
                filterChain);
        // verify if a sendRedirect() was performed with the expected value
        verify(httpServletResponse).sendRedirect("null/pages/index");
    }

}