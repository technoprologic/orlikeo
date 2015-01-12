package umk.zychu.inzynierka.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest arg0,
			HttpServletResponse arg1, Authentication arg2) throws IOException,
			ServletException {
		
		String beforeUrl = arg0.getParameter("beforeurl");
		String contextPath = arg0.getContextPath();
		
		if(StringUtils.hasText(contextPath)){
			if(beforeUrl.startsWith(contextPath)){
				beforeUrl = beforeUrl.substring(contextPath.length());
			}
		}
		
		new DefaultRedirectStrategy().sendRedirect(arg0, arg1, beforeUrl);
	}

}
