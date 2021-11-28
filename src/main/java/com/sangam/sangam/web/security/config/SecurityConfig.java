package com.sangam.sangam.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.sangam.sangam.web.security.CustomUserDetailsService;
import com.sangam.sangam.web.security.RestAuthenticationEntryPoint;
import com.sangam.sangam.web.security.oauth2.AuthenticationSuccessHandler;
import com.sangam.sangam.web.security.oauth2.CustomOAuth2UserService;
//import com.sangam.sangam.web.security.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sangam.sangam.web.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.sangam.sangam.web.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	/*
	 * @Autowired private HttpCookieOAuth2AuthorizationRequestRepository
	 * httpCookieOAuth2AuthorizationRequestRepository;
	 */
	/*
	 * @Bean public TokenAuthenticationFilter tokenAuthenticationFilter() { return
	 * new TokenAuthenticationFilter(); }
	 */
    
	
    
	/*
	 * @Bean public JwtAuthenticationFilter JwtAuthenticationFilter() { return new
	 * JwtAuthenticationFilter(authenticationManager); }
	 */

    
    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
	/*
	 * @Bean public HttpCookieOAuth2AuthorizationRequestRepository
	 * cookieAuthorizationRequestRepository() { return new
	 * HttpCookieOAuth2AuthorizationRequestRepository(); }
	 */

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                 .csrf()
                    .disable()
                .formLogin()
	                .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers
                    ("/",
                    //	"//",
                    	"/login*",
                    	"/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
				//.antMatchers("/index").authenticated()
				 //.antMatchers("/task/**").authenticated()
				    .antMatchers("/auth/**", "/oauth2/**")
                        .permitAll().anyRequest()
                        .authenticated()
                        .and()
                        .formLogin()                       
                        .loginPage("/login")
                        .and()
          //              .addFilter(new JwtAuthenticationFilter(authenticationManager())) 
                .oauth2Login()
                	.authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                    	//.authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                     .successHandler(new AuthenticationSuccessHandler("/member"))
                       // .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        //http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    
	/*
	 * @Bean CorsConfigurationSource corsConfigurationSource() { CorsConfiguration
	 * configuration = new CorsConfiguration();
	 * configuration.setAllowedOrigins(Arrays.asList("/*"));
	 * configuration.setAllowedMethods(Arrays.asList("GET","POST"));
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
	 * configuration); return source; }
	 */
    
	/*
	 * @Bean public CorsConfigurationSource corsConfigurationSource() { final
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",
	 * new CorsConfiguration().applyPermitDefaultValues());
	 * 
	 * return source; }
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
      super.configure(web);
      web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
    
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}
