//1 of 3 controllers - handling custom registration in line with MVC design pattern
package io.github.miwlodar.controller;

import io.github.miwlodar.entity.User;
import io.github.miwlodar.service.UserService;
import io.github.miwlodar.user.CreateUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

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
        final String userName = createUserDto.getUserName();
        LOGGER.info("Processing registration form for: " + userName);

        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        // checking the DB if user already exists
        final User existing = userService.findByUserName(userName);

        if (existing != null) {
            model.addAttribute("CreateUserDto", new CreateUserDto());
            model.addAttribute("registrationError", "User name already exists.");
            LOGGER.info("User name already exists: " + userName);

            return "registration-form";
        }

        userService.save(createUserDto);
        LOGGER.info("Successfully created user: " + userName);

        return "registration-confirmation";
    }
}
