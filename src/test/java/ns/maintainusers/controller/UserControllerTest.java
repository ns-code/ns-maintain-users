package ns.maintainusers.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // @Autowired
    // private UserService userService;

    @Test
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": null, \"name\": \"user2\"}"))
                .andExpect(status().isCreated());
    }    

    @Test
    void shouldReturnConflict() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": null, \"name\": \"user2\"}"))
                .andExpect(status().isConflict());                
    }    

    @Test
    void shouldUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": null, \"name\": \"user1\"}"))
                .andExpect(status().isNoContent());
    }    
    
    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }     

    @Test
    void shouldReturnError() throws Exception {
        mockMvc.perform(delete("/api/users/99"))
                .andExpect(status().isNotFound());
    }     
}
