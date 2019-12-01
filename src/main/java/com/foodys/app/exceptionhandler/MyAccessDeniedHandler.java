package com.foodys.app.exceptionhandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Component("myAccessDeniedHandler")
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    @Qualifier("myRequestCache")
    private RequestCache myRequestCache;

    public MyAccessDeniedHandler() {
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
        throws IOException, ServletException {

        if (!response.isCommitted()) {

            //Save Target-Request
//            shopRequestCache.saveRequest(request, response);

            //Forward to the login page
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }   
}