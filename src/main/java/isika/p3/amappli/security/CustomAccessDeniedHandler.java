package isika.p3.amappli.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
            // Put status 403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // Get Tenancy Alias from Security Context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();

            String tenancyAlias = (String) loggedUserInfo.getAdditionalInfoByKey("tenancyAlias");

            String redirectUrl = "/Amappli/amap/"+tenancyAlias+"/forbidden";

            response.sendRedirect(redirectUrl);
    }
    
}
