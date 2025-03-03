package isika.p3.amappli.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    private final RequestCache requestCache;

    public CustomAuthenticationSuccessHandler(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // System.out.println("do we still know where we came from?");
        // System.out.println(request.getParameter("origin"));
        // System.out.println("Hello from the success handler");
        // String origin = request.getParameter("origin");
        if (savedRequest == null) {
            // No saved request, this means that the user did not try to access
            // a protected endpoint before attempting to log in
            String customPage = determineCustomPage(authentication);
            getRedirectStrategy().sendRedirect(request, response, customPage);
        } else {
            // If there's a saved request, follow the default behavior
            // Which redirect to the resource the user was trying to access
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private String determineCustomPage(Authentication authentication) {
        // If amap user, send him to its amap homepage
        // if also plateform user, send him to the amappli homepage
        CustomUserDetails loggedUserInfo = (CustomUserDetails) authentication.getPrincipal();
        if(loggedUserInfo.getAdditionalInfoByKey("tenancyAlias") != null){
            return "/amap/" + loggedUserInfo.getAdditionalInfoByKey("tenancyAlias") + "/home";
        }
        if(loggedUserInfo.getAdditionalInfoByKey("tenancyAlias") == null && !loggedUserInfo.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("gestion plateforme"::equals)){
            return "/amappli/start/creation";
        }
        return "/amappli";
    }
}
