package learn.capstone.controllers;

import learn.capstone.data.FlashcardSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FlashcardSetControllerTest {
    @MockBean
    FlashcardSetRepository repository;

    @Autowired
    MockMvc mvc;
}
