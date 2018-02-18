package com.project.sbz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception{
		AuthenticationTokenFilter authenticationTokenFilter= new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}
	
	public CsrfTokenRepository csrfTokenRepository(){
		CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
		repository.setCookiePath("/");
		return repository;
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()
				.antMatchers("/", "/api/login", "/api/register", "/api/role", "/api/user", "/api/username-unique",
						"/bower_components/**", "/css/**", "/images/**", "/core/scripts/home/**",
						"/core/scripts/user/**", "/core/scripts/app.js", "/core/views/modals/register.html",
						"/core/views/login.html", "/core/services/**", "/core/views/home.html", "/core/views/homepage.html", "/core/views/profile.html",
						"/core/scripts/article/**", "/core/scripts/category/**", "/core/scripts/customer/**", 
						"/core/views/shopping-history.html", "/core/views/shopping-cart.html", "/core/views/modals/empty-cart.html",
						"/core/views/customer-categories.html", "/core/views/article-categories.html", "/core/views/discount-events.html", 
						"/core/scripts/customer-category/**", "/core/scripts/manager/**", "/core/scripts/article-category/**",
						"/core/views/modals/new-update-threshold.html", "/core/views/modals/update-customer-category.html", 
						"/core/views/modals/delete-threshold.html", "/core/views/modals/update-category-thresholds.html",
						"/core/views/modals/delete-article-category.html", "/core/views/modals/new-update-article-category.html",
						"/core/views/modals/delete-discount-event.html", "/core/views/modals/new-update-discount-event.html", 
						"/core/scripts/discount-event/**", "/core/directives/**","/core/views/process-bills.html", 
						"/core/views/order-articles.html", "/core/scripts/salesman/**", "/core/scripts/bill/**", 
						"/core/views/modals/process-bill.html", "/core/views/modals/approve-bill.html", 
						"/core/views/modals/refuse-bill.html", "/core/views/modals/checkout.html", 
						"/core/views/modals/error-discount-event.html", "/core/views/modals/bill-checkout-info.html").permitAll()
				.antMatchers("/api/customer-category", "/api/article-category", "/api/discount-event", "/api/category-no-goods")
					.hasAuthority("ROLE_MANAGER")
				.antMatchers("/api/search", "/api/user-profile", "/api/shopping-history","/api/checkout")
					.hasAuthority("ROLE_CUSTOMER")
				.antMatchers("/api/bill", "/api/approve-bill", "/api/refuse-bill", "/api/articles", "/api/order-articles")
					.hasAuthority("ROLE_SALESMAN")
				.anyRequest().authenticated();
				//.and().csrf().csrfTokenRepository(csrfTokenRepository());
		httpSecurity.csrf().disable();
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}
}
