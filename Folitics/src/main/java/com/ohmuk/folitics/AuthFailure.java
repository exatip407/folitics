package com.ohmuk.folitics;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ohmuk.folitics.dto.ResponseDto;
import com.ohmuk.folitics.util.Serialize_JSON;

/**
 * This is custom authentication failure handler
 * 
 * @author Mayank Sharma
 *
 */
@Component
public class AuthFailure extends SimpleUrlAuthenticationFailureHandler {
    // private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static Logger logger = LoggerFactory.getLogger(AuthFailure.class);

    /**
     * This callback method is called when authentication fail
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        /* When authentication fail redirect to login page */
       // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        // redirectStrategy.sendRedirect(request, response, "/login");
        logger.debug("User authenticatio fail");
        logger.debug("Redirect to login page");
    }

}
