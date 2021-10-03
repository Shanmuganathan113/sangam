package com.sangam.sangam.web.security.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	    public AuthenticationSuccessHandler(String defaultTargetUrl) {
	        setDefaultTargetUrl(defaultTargetUrl);
	    }

		@Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        if (session != null) {
	            String redirectUrl = (String) session.getAttribute("url_prior_login");
	            if (redirectUrl != null) {
	            	if(redirectUrl.endsWith("/login"))
	            		redirectUrl = "/member";
	            	
	                session.removeAttribute("url_prior_login");
	                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
	            } else {
	                super.onAuthenticationSuccess(request, response, authentication);
	            }
	        } else {
	            super.onAuthenticationSuccess(request, response, authentication);
	        }
	    }
	}