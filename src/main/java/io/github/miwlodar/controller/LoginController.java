//1 of 3 controllers - handling login page (and access denied)

package io.github.miwlodar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		return "custom-login";
		
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {
		
		return "access-denied";
		
	}
}