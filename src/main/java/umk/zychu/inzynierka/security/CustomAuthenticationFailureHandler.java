package umk.zychu.inzynierka.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2) throws IOException, ServletException {
		
		String beforeUrl = arg0.getParameter("beforeurl");
		String contextPath = arg0.getContextPath();
		
		if(StringUtils.hasText(contextPath)){
			if(beforeUrl.startsWith(contextPath)){
				beforeUrl = beforeUrl.substring(contextPath.length());
			}
		}
		
		String url = beforeUrl + "?loginerror=true";
		new DefaultRedirectStrategy().sendRedirect(arg0, arg1, url);
	}

}
