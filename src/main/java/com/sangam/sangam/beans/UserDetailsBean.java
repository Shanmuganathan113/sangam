package com.sangam.sangam.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sangam.sangam.domain.Employee;
import com.sangam.sangam.domain.User;
import com.sangam.sangam.service.EmployeeService;
import com.sangam.sangam.web.security.repository.UserRepository;

import lombok.Data;

@Component
@Scope("session")
@Data
public class UserDetailsBean {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
    private UserRepository userRepository;
	
	private User user = new User();
	private String viewInd ;
;	private String homeMsg = "Welcome to Tamil Sangam4. You can contact us by submitting the below form";

	private String text = "Hellow shan";
	List<String> listMyTeams = new ArrayList<String>();

	private Employee employee = new Employee();

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String register() {
		System.out.println(" *****************  Inside register *********");
		
		return "/slideShow";
		//return "/slideShowQuiz?faces-redirect = true";
	}
	
	public UserDetailsBean() { 
		  System.out.println("Creating object for register employee .."); 
		  this.text = this.getCurrentUser();
		  System.out.println("user name is .."+this.getCurrentUser());
	}

    protected String getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user ::::: "+loggedInUser.getName());
        
        user.setEmail(loggedInUser.getName());
        return loggedInUser.getName();
    }
    
    @PostConstruct
    public void getUserDetails() {
    	System.out.println("Email "+user.getEmail() +" profile pic "+user.getImageUrl());
    	Optional<User> userOptional = null;
    	userOptional = userRepository.findByEmail(user.getEmail());
    	
	    if( userOptional.isPresent()) {
	    	System.out.println("Present "+userOptional.get().getName());	
	    	this.user = userOptional.get();
	    }
	    this.loadMemberDetails("HomeMsg");
	    
    }
    
    public void fetchUserDetails() {
    	System.out.println("Email "+user.getEmail() +" profile pic "+user.getImageUrl());
    	System.out.println(" Exists by email  Exists check boolean: "+userRepository.existsByEmail(user.getEmail()));
    	user = userRepository.findByEmail(user.getEmail()).get(); 
    }
    
	  // *********************************************
	 
	  	private static final long serialVersionUID = 1L;
		private String localeCode;

		private static Map<String,Object> countries;
		static{
			countries = new LinkedHashMap<String,Object>();
			
			Locale french = new Locale("fr","FL");
			countries.put("French", french);
			
			Locale hindi = new Locale("hi","IN");
			countries.put("Hindi", hindi);
			
			Locale engUS = new Locale("en","US");
			countries.put("English-US", engUS);
			
		}
		public Map<String, Object> getCountriesInMap() {
			return countries;
		}
		public String getLocaleCode() {
			return localeCode;
		}
		public void setLocaleCode(String localeCode) {
			this.localeCode = localeCode;
		}

		public void countryLocaleCodeChanged(ValueChangeEvent e){
			String newLocaleValue = e.getNewValue().toString();
	        for (Map.Entry<String, Object> entry : countries.entrySet()) {
	        	if(entry.getValue().toString().equals(newLocaleValue)){
	        		FacesContext.getCurrentInstance()
	        			.getViewRoot().setLocale((Locale)entry.getValue());
	        	}
	        }
		}
		
		
		// =============================================================
		
		@Autowired 
		TaskBean taskBean;
		
		public void testPrint() {
			System.out.println(" Print from session bean >>>");
		}
		
		public void loadMemberDetails(String viewInd) {
			if(viewInd.equals("HomeMsg"))
				//this.listMyTeams = taskBean.fetchMyTeams(this.user.getId());
			this.viewInd = viewInd;
		}

}
