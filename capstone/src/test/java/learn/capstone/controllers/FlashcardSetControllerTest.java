package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.FlashcardSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlashcardSetControllerTest {
    @MockBean
    FlashcardSetRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindAll() throws Exception {
        List<FlashcardSet> expected = List.of(
                new FlashcardSet(1, "Test Title 1"),
                new FlashcardSet(2, "Test Title 2")
        );

        when(repository.findAll()).thenReturn(expected);

        String expectedJson = TestHelpers.serializeObjectToJson(expected);

        mvc.perform(get("/flashcard/set/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindByFlashcardSetId() throws Exception {
        FlashcardSet expected = new FlashcardSet(1, "Test Title 1");

        when(repository.findByFlashcardSetId(1)).thenReturn(expected);

        String expectedJson = TestHelpers.serializeObjectToJson(expected);

        mvc.perform(get("/flashcard/set/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn412WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        FlashcardSet flashcardSet = new FlashcardSet();
        String flashcardSetJson = jsonMapper.writeValueAsString(flashcardSet);

        var request = post("/flashcard/set/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardSetJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn412WhenBlankData() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        FlashcardSet flashcardSet = new FlashcardSet(1, " ");
        String flashcardSetJson = jsonMapper.writeValueAsString(flashcardSet);

        var request = post("/flashcard/set/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardSetJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn412WhenNullData() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        FlashcardSet flashcardSet = new FlashcardSet(1, null);
        String flashcardSetJson = jsonMapper.writeValueAsString(flashcardSet);

        var request = post("/flashcard/set/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardSetJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn201() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(0, "Test Title");
        FlashcardSet expected = new FlashcardSet(1, "Test Title");

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String flashcardSetJson = jsonMapper.writeValueAsString(flashcardSet);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/flashcard/set/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardSetJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldDeleteAndReturn204() throws Exception {
        when(repository.deleteByFlashcardSetId(1)).thenReturn(true);

        mvc.perform(delete("/flashcard/set/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingAndReturn404() throws Exception {
        mvc.perform(delete("/flashcard/set/delete/100"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldUpdateValidAndReturn204() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");

        when(repository.update(any())).thenReturn(true);

        String jsonIn = TestHelpers.serializeObjectToJson(flashcardSet);

        var request = put("/flashcard/set/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateMismatchedIdsAndReturn404() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(4, "Test Title 4");

        String jsonIn = TestHelpers.serializeObjectToJson(flashcardSet);

        var request = put("/flashcard/set/update/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdateInvalidAndReturn412() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(5, "Test Title 5");
        flashcardSet.setTitle(" ");

        String jsonIn = TestHelpers.serializeObjectToJson(flashcardSet);

        var request = put("/flashcard/set/update/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldNotUpdateMissingAndReturn409() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(6, "Test Title 6");

        String jsonIn = TestHelpers.serializeObjectToJson(flashcardSet);

        var request = put("/flashcard/set/update/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

}
