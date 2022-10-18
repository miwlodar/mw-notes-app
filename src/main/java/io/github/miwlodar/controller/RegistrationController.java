//1 of 3 controllers - handling custom registration in line with MVC design pattern
package io.github.miwlodar.controller;

import io.github.miwlodar.entity.User;
import io.github.miwlodar.service.UserService;
import io.github.miwlodar.user.CreateUserDto;
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
    public String showRegistrationForm(Model model) {
        model.addAttribute("CreateUserDto", new CreateUserDto());

        return "registration-form";
    }

    @PostMapping("/process-registration-form")
    public String processRegistrationForm(
            @Valid @ModelAttribute("CreateUserDto") CreateUserDto createUserDto,
            BindingResult bindingResult,
            Model model) {

        String userName = createUserDto.getUserName();
        logger.info("Processing registration form for: " + userName);

        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        // checking the DB if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("CreateUserDto", new CreateUserDto());
            model.addAttribute("registrationError", "User name already exists.");
            logger.warning("User name already exists.");

            return "registration-form";
        }

        userService.save(createUserDto);
        logger.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }
}
