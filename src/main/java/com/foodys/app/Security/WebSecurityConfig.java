package com.foodys.app.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.foodys.app.filters.JwtRequestFilter;

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
    @Qualifier("myRequestCache")
    RequestCache myRequestCache;

    @Autowired
    @Qualifier("myAccessDeniedHandler")
    AccessDeniedHandler myAccessDeniedHandler;
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll()
				.antMatchers("/provider/registration").permitAll()
				.antMatchers("/driver/registration").permitAll()
				.antMatchers("/authenticate").permitAll()
				.antMatchers("/driver_registration").permitAll()
				.antMatchers("/provider_registration").permitAll()
				.anyRequest().authenticated()
				.and()
					.logout()
					.deleteCookies("foodys-temp")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/").and().exceptionHandling()
					.accessDeniedPage("/")
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.requestCache()
	            .requestCache(myRequestCache)
	            .and()
	            .exceptionHandling()
	            .accessDeniedHandler(myAccessDeniedHandler);
	            
				
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring() // we allow page to access these resources for these no need to authenticate
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}


}