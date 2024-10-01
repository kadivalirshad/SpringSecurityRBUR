package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.example.demo.Exception.GlobalExceptionHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private OAuthSuccessHandler authSuccessHandler;
	  
	@Autowired
	private GlobalExceptionHandler globalException;
	
	 @Autowired
	  private ClientRegistrationRepository clientRegistrationRepository;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/user/checkUserName", "/user/login","user/checkUserLogin").permitAll()
				.requestMatchers("/user/users/**", "/user/view/**").hasAnyAuthority("VIEW")
				.requestMatchers("/user/users/**", "/user/edit/**","/user/register/update/**").hasAnyAuthority("UPDATE")
				.requestMatchers("/user/delete/**").hasAnyAuthority("DELETE")
				.requestMatchers("/user/register/**", "/Admin/userPermission/**").hasAnyAuthority("CREATE").anyRequest()
				.authenticated())
		        .formLogin(form -> form.loginPage("/user/login")
						.failureUrl("/user/login?error=true").permitAll())
		        
				.oauth2Login(oauth2 -> {
					oauth2.loginPage("/user/login");
					oauth2.successHandler(authSuccessHandler);
					oauth2.authorizationEndpoint((authorizationEndpointConfig ->
                    authorizationEndpointConfig.authorizationRequestResolver(
                            requestResolver(this.clientRegistrationRepository))));
					oauth2.failureHandler(globalException);
				})
				
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll())
				.exceptionHandling(
						eh -> eh.accessDeniedPage("/user/403").authenticationEntryPoint(authenticationEntryPoint) 
				);

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/webjars/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	private static DefaultOAuth2AuthorizationRequestResolver requestResolver
    (ClientRegistrationRepository clientRegistrationRepository) {
      DefaultOAuth2AuthorizationRequestResolver requestResolver =
        new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                "/oauth2/authorization");
         requestResolver.setAuthorizationRequestCustomizer(c ->
        c.attributes(stringObjectMap -> stringObjectMap.remove(OidcParameterNames.NONCE))
                .parameters(params -> params.remove(OidcParameterNames.NONCE))
       );
      return requestResolver;
    }
	
	
	
	
	
	
}
