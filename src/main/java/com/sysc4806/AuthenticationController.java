package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextListener;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by richardcarson3 on 3/23/2017.
 */
@EnableOAuth2Sso
@Configuration
@Controller
public class AuthenticationController extends WebSecurityConfigurerAdapter {
    private static UserRepository userRepository;

    public AuthenticationController(UserRepository userRepository) {
        AuthenticationController.userRepository = userRepository;
    }

    @RequestMapping("/preferences")
    public String preferences(Model model) {
        model.addAttribute("user", AuthenticationController.CurrentUser());
        return "authentication/preferences";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/", "/login**", "/webjars/**").permitAll()
            .anyRequest().authenticated()
            .and().logout().logoutSuccessUrl("/").permitAll();
    }

    /**
     * Get logged in user
     * @return User or null if not logged in
     */
    public static User CurrentUser() {
        Map<String, Object> details = CurrentUserDetails();
        if (details == null) return null;

        User user = userRepository.findByEmail((String)details.get("email"));
        if (user == null) user = new User((String)details.get("email"));
        user.setName((String)details.get("name"));

        userRepository.save(user);
        return user;
    }

    /**
     * Extract user detail information from a session
     * @return Map of properties
     */
    private static Map<String, Object> CurrentUserDetails() {
        // Get current authentication object if possible
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null || a instanceof AnonymousAuthenticationToken) return null;

        // Retrieve access token
        Map<String, Object> details;
        Field field;
        try {
            // Retrieve using reflection because spring will not return the correct implementation
            field = a.getClass().getDeclaredField("userAuthentication");
            field.setAccessible(true);

            // Get token details
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) field.get(a);
            details = (Map<String, Object>) token.getDetails();
        } catch (Exception e) {
            // Default if there IS a valid user, just not the one we expect
            // Useful in testing
            details = new HashMap<String, Object>();
            details.put("name", a.getName());
            details.put("email", a.getName());
        }

        return details;
    }

    /**
     * Prevent issue with token contexts
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
}
