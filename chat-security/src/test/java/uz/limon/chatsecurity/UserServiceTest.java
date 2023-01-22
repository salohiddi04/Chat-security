package uz.limon.chatsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MockMvc;
import uz.limon.chatsecurity.dto.ChatDTO;
import uz.limon.chatsecurity.dto.ResponseDTO;
import uz.limon.chatsecurity.dto.UserDTO;
import uz.limon.chatsecurity.mapper.ChatMapper;
import uz.limon.chatsecurity.mapper.UserMapper;
import uz.limon.chatsecurity.model.Chat;
import uz.limon.chatsecurity.model.User;
import uz.limon.chatsecurity.repository.ChatRepository;
import uz.limon.chatsecurity.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatRepository chatRepository;


    @Autowired
    private ObjectMapper objectMapper;

    UserServiceTest() throws Exception {
    }

    @Test
    @Order(1)
    void checkUserAdd() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Qosim_u");
        userDTO.setPassword("qOsim5");
        userDTO.setFirstName("Qosim");
        userDTO.setLastName("Sattorov");
        userDTO.setPhoneNumber("+99893 545 55 66");

        String s = mockMvc.perform(post("/user/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO)))
                .andReturn().getResponse().getContentAsString();

        ResponseDTO<?> responseDTO = objectMapper.readValue(s, ResponseDTO.class);
        Assertions.assertNotNull(responseDTO.getData());
        Optional<User> user = Optional.empty();
        if(responseDTO.getSuccess()){
            user = userRepository.findById((Integer) responseDTO.getData());
            if (user.isEmpty()){
                Assertions.fail("User is null");
            }
            UserDTO userDTO2 = userMapper.toDTO(user.get());
            Assertions.assertEquals(userDTO.getUsername(), userDTO2.getUsername());
        } else {
            Assertions.fail(responseDTO.getErrors().toString());
        }
        System.out.println(responseDTO);
    }

    @Test
    @Order(2)
    void createChat() throws Exception {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("MyClub");


        String s = mockMvc.perform(post("/chats/add")
                        .contentType("application/json")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjczOTIzMjAsInN1YiI6IjkzQTBGNjNEOUVGQzRFQTg4MUM0OTFFQzg0OTFFRTUwIiwiaWF0IjoxNjY3MzI5MjAwfQ.9WcxNAeC5EPcvKldYbQJpYdWqYmEubncGjFR7o4-j8k")
                        .content(objectMapper.writeValueAsString(new ArrayList<Integer>(List.of(1)))))
                .andReturn().getResponse().getContentAsString();

        ResponseDTO<?> responseDTO = objectMapper.readValue(s, ResponseDTO.class);
        Assertions.assertNotNull(responseDTO);

        if(!responseDTO.getSuccess()){
            Assertions.fail(responseDTO.getErrors().toString());
        }
        Optional<Chat> chat = chatRepository.findById((Integer) responseDTO.getData());
        if(chat.isEmpty()){
            Assertions.fail("Chat is empty");
        }

        ChatDTO chatDTO2 = chatMapper.toDto(chat.get());
        Assertions.assertEquals(chatDTO2.getName(), chatDTO.getName());

        chatRepository.delete(chat.get());

        System.out.println(responseDTO);
    }

}
