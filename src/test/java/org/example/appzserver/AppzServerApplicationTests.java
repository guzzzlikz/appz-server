package org.example.appzserver;

import org.example.appzserver.controllers.AuthController;
import org.example.appzserver.repositories.UserRepository;
import org.example.appzserver.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testRegisterBlankEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType("application/json")
                .content("""
            {"name":"Alex","email":"","password":"c"}
        """)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testRegisterBlankName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType("application/json")
                .content("""
            {"name":"","email":"guzzlo","password":"c"}
        """)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testRegisterBlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                .contentType("application/json")
                .content("""
            {"name":"Alex","email":"jgj","password":""}
        """)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
