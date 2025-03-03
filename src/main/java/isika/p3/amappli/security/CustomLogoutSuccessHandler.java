package isika.p3.amappli.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    
     @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            String redirectUrl = "/Amappli/";
            CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();
            String userTenancy = (String) loggedUserInfo.getAdditionalInfoByKey("tenancyAlias");

            // Redirect logic based on roles or other authentication attributes
            if (userTenancy == null){
                // This means that the user did not have a tenancy and is either an AMAPPLI admin or a new AMAPPLI user who hasn't created his amap space
                redirectUrl += "amappli";
            }
            else {
                redirectUrl += "amap/" + userTenancy + "/home";
            }

            response.sendRedirect(redirectUrl);
        } else {
            // If user was not connected, send him back to amappli homepage
            response.sendRedirect("/Amappli/amappli");
        }
    }
}
