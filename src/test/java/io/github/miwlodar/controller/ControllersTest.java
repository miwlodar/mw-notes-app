package io.github.miwlodar.controller;

import io.github.miwlodar.service.NotesServiceImpl;
import io.github.miwlodar.service.UsersServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersServiceImpl usersServiceImpl;

    @MockBean
    private NotesServiceImpl notesServiceImpl;

    @Test
    @DisplayName("Home page works properly")
    public void homePageWorksProperly() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "forward:index.html");
    }

    @Test
    @DisplayName("Login page works properly")
    public void loginPageWorksProperly() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/show-my-login-page"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "custom-login");
    }

    @Test
    @DisplayName("Registration form is accessible for any user")
    public void registrationAccessible() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/register/show-registration-form"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "registration-form");
    }

    @Test
    @DisplayName("Unauthorised user can't access notes")
    public void notesPageInaccessibleByUnauthorizedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notes")).andExpect(status().is(302));
    }

    @Test
    @DisplayName("Authorised user can access their own notes")
    @WithMockUser(username = "johnny", roles = {"USER"})
    public void notesAccessibleForUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "/notes/list-notes");
    }
}
