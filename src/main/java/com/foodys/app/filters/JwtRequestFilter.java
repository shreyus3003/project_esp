package com.foodys.app.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.foodys.app.service.MyUserDetailsService;
import com.foodys.app.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

    	

        String username = null;
        String jwt = null;

//        If jwt is in authorization header
    	
      final String authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(jwt);
      }else {
    	
        // Jwt in cookie only one time once authenticated to use window.location.href.
        
        Cookie [] cookies = request.getCookies();
        if(cookies!=null) {
    	for (Cookie cookie : cookies) {
    	     if ("foodys-temp".equals(cookie.getName())) {
    	          
    	          if(cookie.getValue()!="success") {
    	        	  jwt = cookie.getValue();
    	        	  username = jwtUtil.extractUsername(jwt);
    	        // only for spa
//    	        	  cookie.setMaxAge(0);
//        	          response.addCookie(cookie);
    	          }
    	         //do something with the cookie's value.
    	     }
    	}
      }
      }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

           boolean flag = false;
           try {
        	   flag = jwtUtil.validateToken(jwt, userDetails);
           }catch(Exception e) {
        	   
        	   System.out.println(e.getMessage());
           }
           
            if (flag) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
            	System.out.println("Reached Invalid token");
            	 request.getRequestDispatcher("/login").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

}
