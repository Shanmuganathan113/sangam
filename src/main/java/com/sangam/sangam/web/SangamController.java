package com.sangam.sangam.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SangamController {
	
//	@RequestMapping("/index")
//	@PreAuthorize("hasRole('USER')")
//    public String home() {
//        return "index";
//    }
//	
	@RequestMapping("/team")
    public String slideShow() {
		return "team";
    }
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("url_prior_login", referrer);
        return "login";
    }
	
	@RequestMapping(value = "/member", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')") 
    public String member() {
        return "member";
    }
	
	@RequestMapping("/questionsAnswers")
    public String questionsAnswers() {
        return "questionsAnswers";
    }
	
	@RequestMapping("/contact-us")
    public String contribute() {
        return "contactUs";
    }
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
            return "";
    }
}
