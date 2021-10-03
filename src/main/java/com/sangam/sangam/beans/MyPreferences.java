package com.sangam.sangam.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Scope("session")
@Data
public class MyPreferences implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Map<String,String> themeColors;
    
    private String theme = "blue";
    
    private String layout = "blue";
    
    private String menuClass = null;
    
    private String profileMode = "inline";
    
    private String menuLayout = "static";

    private String version = "4";
            
    @PostConstruct
    public void init() {
        themeColors = new HashMap<String,String>();
        themeColors.put("blue", "#03A9F4");
    }
    
    public String getMenuClass() {
        return this.menuClass;
    }
    
    public String getProfileMode() {
        return this.profileMode;
    }

	public String getTheme() {		
		return theme + this.version;
	}
    
    public String getLayout() {		
        return layout + this.version;
    }
    
    public String getMenuLayout() {	
        if(this.menuLayout.equals("static"))
            return "menu-layout-static";
        else if(this.menuLayout.equals("overlay"))
            return "menu-layout-overlay";
        else if(this.menuLayout.equals("horizontal"))
            return "menu-layout-static menu-layout-horizontal";
        else if(this.menuLayout.equals("slim"))
            return "menu-layout-static menu-layout-slim";
        else
            return "menu-layout-static";
    }

	public void setTheme(String theme) {
		this.theme = theme;
	}
    
    public void setLayout(String layout, boolean special) {
        this.layout = layout;
        if(special) {
            this.setDarkMenu();
        }
    }
    
    public void setLightMenu() {
        this.menuClass = null;
    }
    
    public void setDarkMenu() {
        this.menuClass = "layout-menu-dark";
    }
    
    public void setProfileMode(String profileMode) {
        this.profileMode = profileMode;
    }
    
    public void setMenuLayout(String menuLayout) {
        this.menuLayout = menuLayout;
    }
    
    public Map getThemeColors() {
        return this.themeColors;
    }

    public String getVersion() {		
		return version;
	}
    
    public void setVersion(String version) {
        if(version.equals("3")) {
            this.version = "";
        }	
        else if(version.equals("4")) {
            this.version = "-v4";
        }	
    }
}
