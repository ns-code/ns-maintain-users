package ns.maintainusers.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ns.maintainusers.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    // @Mock
    // private UserRepository userRepository;

    @Test
    @Order(3)
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void shouldCreateNewUser() throws Exception {
        User newUser = new User(null, "u432", "fname4", "lname4", "email4@test.com", 'I', null); 
        System.out.println(">>?? toCreate : "+objectMapper.writeValueAsString(newUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
    }    

    @Test
    @Order(2)
    void shouldReturnConflict() throws Exception {
        User newUser = new User(null, "u432", "fname4", "lname4", "email4@test.com", 'I', null); 

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isConflict());                
    }    

    @Test
    @Order(4)
    void shouldUpdateUser() throws Exception {
        User newUser = new User(null, "u432", "fname4", "lname4", "email4@test.com", 'A', null);
                
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isNoContent());
    }    
    
    @Test
    @Order(5)
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }     

    @Test
    @Order(6)
    void shouldReturnError() throws Exception {
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());
    }     
}
