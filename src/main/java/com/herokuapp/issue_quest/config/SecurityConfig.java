package com.herokuapp.issue_quest.config;

import com.herokuapp.issue_quest.auth.service.IssueQuestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring-security設定クラス
 * @author obscure12
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("issueQuestUserDetailsService")
	private IssueQuestUserDetailsService issueQuestUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/login/", "/common/**", "/css/**", "/fonts/**", "/js/**", "/message/**").permitAll()
				.anyRequest().fullyAuthenticated()
				.and()
			.formLogin()
				.loginPage("/login/")
	            .usernameParameter("email")
	            .passwordParameter("password")
	            .defaultSuccessUrl("/")
	            .permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login/?logout")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true).permitAll()
				.and()
			.csrf()
				.disable();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
