package isika.p3.amappli.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
            throws IOException, ServletException {

        // Retrieve the original login page from the login request
        String originalLoginPage = request.getParameter("origin");

        if (originalLoginPage == null || originalLoginPage.isEmpty()) {
            // Fallback to a default login page
            response.sendRedirect("/Amappli/login?error=true");
        } else {
            // Add error parameter to the original login page
            if(originalLoginPage.equals("amappli")){
                response.sendRedirect("/Amappli/amappli/login?error=true");
            }
            else{
                response.sendRedirect("/Amappli/amap/"+originalLoginPage+"/login?error=true");
            }
        }

    }

}
