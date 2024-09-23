package learn.capstone.domain;

import learn.capstone.data.FlashcardRepository;
import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.Flashcard;
import learn.capstone.models.FlashcardSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

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
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard expected = new Flashcard(1, flashcardSet, "Test Front", "Test Back");
        when(flashcardRepository.findByFlashcardId(1)).thenReturn(expected);
        assertEquals(expected, service.findByFlashcardId(1));
    }

    @Test
    void shouldFindByFlashcardSetId() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        List<Flashcard> flashcards = List.of(
                new Flashcard(1, flashcardSet, "Test Front", "Test Back"),
                new Flashcard(2, flashcardSet, "Test Front", "Test Back"),
                new Flashcard(3, flashcardSet, "Test Front", "Test Back")
        );
        when(flashcardRepository.findByFlashcardSetId(1)).thenReturn(flashcards);
        assertEquals(flashcards, service.findByFlashcardSetId(1));
    }

    @Test
    void shouldAddValidFlashcard() {
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

    @Test
    void shouldUpdateValidFlashcardData() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard expected = new Flashcard(1, flashcardSet, "Update Front", "Test Back");
        when(flashcardRepository.update(expected)).thenReturn(true);
        Result<Flashcard> result = service.update(expected);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateWithBlankData() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(1, flashcardSet, " ", "Test Back");
        service.add(flashcard);
        Flashcard updatedCard = new Flashcard(1, flashcardSet, " ", "Test Back");
        Result<Flashcard> result = service.update(updatedCard);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard front data must have an input.", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithNullData() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "Test Title");
        Flashcard flashcard = new Flashcard(0, flashcardSet, "Test Front", "Text Back");
        service.add(flashcard);
        Flashcard updatedCard = new Flashcard(1, flashcardSet, "Test Front", null);
        Result<Flashcard> result = service.update(updatedCard);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard back data must have an input.", result.getMessages().get(0));
    }

    @Test
    void shouldNotDeleteNonExistent() {
        Result<Flashcard> result = service.deleteByFlashcardId(100);
        assertFalse(result.isSuccess());
    }

}
