package com.sysc4806;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * Created by richardcarson3 on 3/18/2017.
 */
public class LayoutInterceptor extends HandlerInterceptorAdapter {
    private static final String DEFAULT_LAYOUT = "layouts/default";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }

        User user = AuthenticationController.CurrentUser();

        modelAndView.addObject("user", user);
        String originalViewName = modelAndView.getViewName();
        modelAndView.setViewName(DEFAULT_LAYOUT);
        modelAndView.addObject(DEFAULT_VIEW_ATTRIBUTE_NAME, originalViewName);
    }

    public static Principal CurrentUser(@AuthenticationPrincipal Principal principal) {
        return principal;
    }
}