package io.github.miwlodar.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Checking if the Home page works properly")
    public void homePageWorksProperly() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "forward:index.html");
    }

    @Test
    @DisplayName("Ensuring that unauthorised user can't access notes")
    public void notesPageInaccessibleByUnauthorizedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notes")).andExpect(status().is(302)); //redirecting means that unauthorised users cannot access the notes page
    }





    //TODO zrobic tez zalogowanego usera etc - zaockowac sobie ew SERVICE, bo kontroler dotyka serwisu, a nie bazy
//    @Test
//    @WithMockUser
//    @DisplayName("Ensuring that unauthorised user can't access notes")
//    public void notesPageInaccessibleByUnauthorizedUser() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/notes")).andExpect(status().is(302)); //redirecting means that unauthorised users cannot access the notes page
//    }

    //TODO test that authorised user can access notes page

}
