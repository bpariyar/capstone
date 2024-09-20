package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.data.FlashcardRepository;
import learn.capstone.models.Flashcard;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlashcardControllerTest {
    @MockBean
    FlashcardRepository repository;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldFindByFlashcardId() throws Exception {
        Flashcard expected = makeFlashcard(1);


        when(repository.findByFlashcardId(1)).thenReturn(expected);

        String expectedJson = TestHelpers.serializeObjectToJson(expected);

        mvc.perform(get("/flashcard/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldFindByFlashcardSetId() throws Exception {
        List<Flashcard> expected = List.of(
                makeFlashcard(1),
                makeFlashcard(2),
                makeFlashcard(3)
        );

        when(repository.findByFlashcardSetId(1)).thenReturn(expected);

        String expectedJson = TestHelpers.serializeObjectToJson(expected);

        mvc.perform(get("/flashcard/cards/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void addShouldReturn412WhenInvalid() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Flashcard flashcard = new Flashcard();
        String flashcardJson = jsonMapper.writeValueAsString(flashcard);

        var request = post("/flashcard/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn412WhenBlankData() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, "", "Test Back");
        String flashcardJson = jsonMapper.writeValueAsString(flashcard);

        var request = post("/flashcard/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn412WhenNullData() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, null, "Test Back");
        String flashcardJson = jsonMapper.writeValueAsString(flashcard);

        var request = post("/flashcard/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardJson);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void addShouldReturn201() throws Exception {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, "Test Front", "Test Back");
        Flashcard expected = new Flashcard(1, flashcardSet, "Test Front", "Test Back");

        when(repository.add(any())).thenReturn(expected);
        ObjectMapper jsonMapper = new ObjectMapper();

        String flashcardJson = jsonMapper.writeValueAsString(flashcard);
        String expectedJson = jsonMapper.writeValueAsString(expected);

        var request = post("/flashcard/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(flashcardJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldDeleteAndReturn204() throws Exception {
        when(repository.deleteByFlashcardId(1)).thenReturn(true);

        mvc.perform(delete("/flashcard/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteMissingAndReturn404() throws Exception {
        mvc.perform(delete("/flashcard/delete/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateValidAndReturn204() throws Exception {
        Flashcard flashcard = makeFlashcard(4);

        when(repository.update(any())).thenReturn(true);

        String jsonIn = TestHelpers.serializeObjectToJson(flashcard);

        var request = put("/flashcard/update/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdateMismatchedIdsAndReturn404() throws Exception {
        Flashcard flashcard = makeFlashcard(5);

        String jsonIn = TestHelpers.serializeObjectToJson(flashcard);

        var request = put("/flashcard/update/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdateInvalidAndReturn412() throws Exception {
        Flashcard flashcard = makeFlashcard(6);
        flashcard.setFrontData(" ");

        String jsonIn = TestHelpers.serializeObjectToJson(flashcard);

        var request = put("/flashcard/update/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void shouldNotUpdateMissingAndReturn409() throws Exception {
        Flashcard flashcard = makeFlashcard(7);

        String jsonIn = TestHelpers.serializeObjectToJson(flashcard);

        var request = put("/flashcard/update/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    private FlashcardSet makeFlashcardSet(int flashcardSetId) {
        return new FlashcardSet(flashcardSetId, "Test Set");
    }

    private Flashcard makeFlashcard(int flashcardId) {
        FlashcardSet flashcardSet = makeFlashcardSet(1);
        return new Flashcard(flashcardId, flashcardSet, "Test Front", "Test Back");
    }

}
