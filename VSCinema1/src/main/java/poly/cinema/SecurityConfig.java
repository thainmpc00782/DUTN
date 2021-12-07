package poly.cinema;

import java.util.stream.Collectors;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import poly.cinema.entity.Account;
import poly.cinema.service.AccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AccountService userService;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	HttpSession session;
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = userService.findById(username);
				String password = user.getPassword();
				String[] roles = user.getAuthorities().stream()
						.map(au -> au.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				if(!user.getActivity()) {
					session.setAttribute("error", "Tài khoản bạn đã bị khóa");
					return User.withUsername(username).password(pe.encode(password)).roles(roles).accountLocked(true).build();
				}else {
					session.removeAttribute("error");
					return User.withUsername(username).password(pe.encode(password)).roles(roles).accountLocked(false).build();
				}
			} catch (Exception e) {
				System.out.println(e);
				throw new UsernameNotFoundException(username+"not found!");
			}
		});
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable();
		
		http.authorizeRequests()
		.antMatchers("/rest/authorities","/rest/statistics/*" ,"/rest/censors").hasRole("MOD")
		.antMatchers("/admin/**","/assets/admin/**").hasAnyRole("MOD", "STA")
		.antMatchers("/security/changePassword/form", "/security/updateAccount/form", "/order/**").authenticated()
		.anyRequest().permitAll();
	
		http.formLogin()
			.loginPage("/security/login/form")
			.loginProcessingUrl("/security/login")
			.defaultSuccessUrl("/security/login/success", false)
			.failureUrl("/security/login/error");
		
		http.exceptionHandling()
			.accessDeniedPage("/security/login/form");
		
		http.logout()
			.logoutUrl("/security/logoff")
			.logoutSuccessUrl("/security/logoff/success");
	}
	
}