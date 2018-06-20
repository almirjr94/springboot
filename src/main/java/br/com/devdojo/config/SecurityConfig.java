package br.com.devdojo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.devdojo.service.CustomUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.authorizeRequests()
//			.antMatchers("/*/protected/**").hasRole("USER")
//			.antMatchers("/*/admin/**").hasRole("ADMIN")
//			.and()
//			.httpBasic()
//			.and()
//			.csrf().disable();
//		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and().csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, SecurityConstants.SIGN_UP_URL).permitAll()
			.antMatchers("/*/protected/**").hasRole("USER")
			.antMatchers("/*/admin/**").hasRole("ADMIN")
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));

			
		}
		

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		}
	
		/*@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
			auth.inMemoryAuthentication().
				withUser("almir").password("{noop}1234").roles("USER")
				.and()
				.withUser("admin").password("{noop}1234").roles("USER","ADMIN");
		}*/
}
