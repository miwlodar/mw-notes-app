//1 of 3 controllers - handling custom registration in line with MVC design pattern
package io.github.miwlodar.controller;

import io.github.miwlodar.entity.Users;
import io.github.miwlodar.service.UserService;
import io.github.miwlodar.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;
	
    private final Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/show-registration-form")
	public String showMyLoginPage(Model model) {
		
		model.addAttribute("crmUser", new CrmUser());
		
		return "registration-form";
	}

	@PostMapping("/process-registration-form")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser crmUser,
				BindingResult bindingResult,
				Model model) {
		
		String userName = crmUser.getUserName();
		logger.info("Processing registration form for: " + userName);

		 if (bindingResult.hasErrors()){
			 return "registration-form";
	        }

		// checking the DB if user already exists
        Users existing = userService.findByUserName(userName);
        if (existing != null){
        	model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name already exists.");

			logger.warning("User name already exists.");
        	return "registration-form";
        }

        userService.save(crmUser);
        
        logger.info("Successfully created user: " + userName);
        
        return "registration-confirmation";		
	}
}
