package learn.capstone.domain;

import learn.capstone.data.FlashcardRepository;
import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.Flashcard;
import learn.capstone.models.FlashcardSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FlashcardServiceTest {

    @MockBean
    FlashcardSetRepository flashcardSetrepository;

    @MockBean
    FlashcardRepository flashcardRepository;

    @Autowired
    FlashcardService service;

    @Test
    void shouldFindByFlashcardId() {

    }

    @Test
    void shouldFindByFlashcardSetId() {

    }

    @Test
    void shouldAddValidFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, "Test Front", "Test Back");
        Flashcard expected = new Flashcard(1, flashcardSet, "Test Front", "Test Back");
        when(flashcardRepository.add(flashcard)).thenReturn(expected);
        Result<Flashcard> result = service.add(flashcard);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddWithBlankData() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, " ", "Test Back");
        Result<Flashcard> result = service.add(flashcard);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard front data must have an input.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithNullData() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, "Test Front", null);
        Result<Flashcard> result = service.add(flashcard);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard back data must have an input.", result.getMessages().get(0));
    }

}
