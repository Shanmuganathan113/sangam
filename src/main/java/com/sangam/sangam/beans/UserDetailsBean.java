package com.sangam.sangam.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sangam.sangam.domain.User;
import com.sangam.sangam.web.security.repository.UserRepository;

import lombok.Data;

@Component
@Scope("session")
@Data
public class UserDetailsBean {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired 
	TaskBean taskBean;
	
	private User user = new User();
	private String viewInd ;
;	private String homeMsg = "Welcome to Tamil Sangam4. You can contact us by submitting the below form";

	List<String> listMyTeams = new ArrayList<String>();

	public String register() {
		return "/slideShow";
	}
	
    protected String getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        user.setEmail(loggedInUser.getName());
        return loggedInUser.getName();
    }
    
    @PostConstruct
    public void getUserDetails() {
    	Optional<User> userOptional = null;
    	userOptional = userRepository.findByEmail(user.getEmail());
    	
	    if( userOptional.isPresent()) {
	    	this.user = userOptional.get();
	    }
	    this.loadMemberDetails("HomeMsg");
	    
    }
    
    public void fetchUserDetails() {
    	user = userRepository.findByEmail(user.getEmail()).get(); 
    }
    
//	  	private static final long serialVersionUID = 1L;
//		private String localeCode;
//
//		private static Map<String,Object> countries;
//		static{
//			countries = new LinkedHashMap<String,Object>();
//			
//			Locale french = new Locale("fr","FL");
//			countries.put("French", french);
//			
//			Locale hindi = new Locale("hi","IN");
//			countries.put("Hindi", hindi);
//			
//			Locale engUS = new Locale("en","US");
//			countries.put("English-US", engUS);
//			
//		}
//		public Map<String, Object> getCountriesInMap() {
//			return countries;
//		}
//		public String getLocaleCode() {
//			return localeCode;
//		}
//		public void setLocaleCode(String localeCode) {
//			this.localeCode = localeCode;
//		}
//
//		public void countryLocaleCodeChanged(ValueChangeEvent e){
//			String newLocaleValue = e.getNewValue().toString();
//	        for (Map.Entry<String, Object> entry : countries.entrySet()) {
//	        	if(entry.getValue().toString().equals(newLocaleValue)){
//	        		FacesContext.getCurrentInstance()
//	        			.getViewRoot().setLocale((Locale)entry.getValue());
//	        	}
//	        }
//		}
//		
		
		public void loadMemberDetails(String viewInd) {
			if(viewInd.equals("HomeMsg"))
			this.viewInd = viewInd;
		}

}
