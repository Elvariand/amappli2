package isika.p3.amappli.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        // Get the tenancyAlias from the request
        String uri = request.getRequestURI();
        String loginPageAddress = determineLoginPageAddress(uri);

        authException.printStackTrace();
        response.sendRedirect(loginPageAddress);
    }

    private String determineLoginPageAddress(String uri){
        // Check if  URI starts with /amap/{tenancyAlias}/something (meaning we need to go the the appropriate tenancy login)
        int slashIndex = uri.indexOf("/",14); // check if there's a slash after the tomcat /Amappli/amap/

        if(uri.startsWith("/Amappli/amap/") && slashIndex > 14){ // there is a /part/ after amap
            String potentialTenancyAlias = uri.substring(14, slashIndex);
            if(!potentialTenancyAlias.equals("amappli")){ //if the potentialTenancyAlias is Amappli, that means that the uri is in fact a platform URI
                return "/Amappli/amap/" + potentialTenancyAlias + "/login";
            }
        }

        return "/Amappli/amappli/login";
    }
    
}
