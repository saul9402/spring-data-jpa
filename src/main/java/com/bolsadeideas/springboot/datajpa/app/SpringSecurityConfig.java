package com.bolsadeideas.springboot.datajpa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsadeideas.springboot.datajpa.app.auth.handler.LoginSuccessHandler;

//esta anotacion es importante para habilitar el uso de anotaciones en los metodos y sustituye a la configuracion que se ve comentada aqui
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				/*
				 * .antMatchers("/uploads/** ").hasAnyRole("USER")
				 * .antMatchers("/ver/**").hasAnyRole("USER")
				 * .antMatchers("/form/**").hasAnyRole("ADMIN").antMatchers("/eliminar/**").
				 * hasAnyRole("ADMIN") .antMatchers("/factura/**").hasAnyRole("ADMIN")
				 */
				.anyRequest().authenticated().and().formLogin().successHandler(successHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/error_403");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		/*
		 * Forma 1 UserBuilder users = User.builder().passwordEncoder(password -> {
		 * return encoder.encode(password); });
		 */

		/*
		 * Forma 2 UserBuilder users = User.builder().passwordEncoder(password ->
		 * encoder.encode(password));
		 */
		/*
		 * Forma 3 la más bonita, :3
		 */
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		builder.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
				.withUser(users.username("andres").password("12345").roles("USER"));

	}

}
