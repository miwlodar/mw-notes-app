//class for handling the request after successful authentication - either using Google or in a custom way

package io.github.miwlodar.config;

import io.github.miwlodar.entity.Users;
import io.github.miwlodar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String userName = authentication.getName();
		
		System.out.println("Logged-in user = " + userName);

		Users theUser = userService.findByUserName(userName);

		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		
		response.sendRedirect(request.getContextPath() + "/");
	}
}