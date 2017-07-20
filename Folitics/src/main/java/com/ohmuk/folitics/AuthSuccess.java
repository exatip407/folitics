package com.ohmuk.folitics;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.service.IUserService;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * This is our custom authentication success handler
 * @author Mayank Sharma
 *
 */
@Component
public class AuthSuccess extends SimpleUrlAuthenticationSuccessHandler {
    
    @Autowired
    IUserService userService;
    /**
     * RedirectStrategy is use to redirect page
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static Logger logger = LoggerFactory.getLogger(AuthSuccess.class);

    /**
     * This callback method is called when authentication is success
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        HttpSession session = request.getSession();

        User authUser = (User) authentication.getPrincipal();
        session.setAttribute("user", authUser);
        session.setAttribute("userName", authUser.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());
        logger.debug("User is authenticated with username: " + authUser.getUsername() + " authenticaion: "
                + authUser.getAuthorities());

        /* Set target URL to redirect */
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        try {
            response.getWriter().write(Serialize_JSON.getJSONString(new ResponseDto<Object>(true, authUser, "Welcome "+authUser.getUsername())));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //String targetUrl = determineTargetUrl(authentication);
       // redirectStrategy.sendRedirect(request, response, targetUrl);
        
    }

    /**
     * This mehtod determine target url for user based on there role
     * 
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains("ROLE_ADMIN")) {
            return "/403.html";
        } else if (authorities.contains("ROLE_USER")) {
            return "/index.html";
        } else {
            throw new IllegalStateException();
        }
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}