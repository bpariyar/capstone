package learn.capstone.domain;

import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.FlashcardSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FlashcardSetServiceTest {

    @MockBean
    FlashcardSetRepository repository;

    @Autowired
    FlashcardSetService service;


    @Test
    void shouldFindAll() {
        List<FlashcardSet> expected = List.of(
                new FlashcardSet(1, "Test Title 1"),
                new FlashcardSet(2, "Test Title 2")
        );

        when(repository.findAll()).thenReturn(expected);
        List<FlashcardSet> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByFlashcardSetId() {
        FlashcardSet expected = new FlashcardSet(1, "Test Title 1");
        when(repository.findByFlashcardSetId(1)).thenReturn(expected);
        FlashcardSet actual = service.findByFlashcardSetId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddValidFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet(0, "Test Title");
        FlashcardSet expected = new FlashcardSet(1, "Test Title");
        when(repository.add(flashcardSet)).thenReturn(expected);
        Result<FlashcardSet> result = service.add(flashcardSet);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddWithBlankTitle() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "");
        Result<FlashcardSet> result = service.add(flashcardSet);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard set title must have an input.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithNullTitle() {
        FlashcardSet flashcardSet = new FlashcardSet(1, null);
        Result<FlashcardSet> result = service.add(flashcardSet);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Flashcard set title must have an input.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithExistingTitle() {
        FlashcardSet flashcardSet = new FlashcardSet(1, "test");
        FlashcardSet flashcardSetDuplicate = new FlashcardSet(1, "test");
        service.add(flashcardSet);
        Result<FlashcardSet> result = service.add(flashcardSetDuplicate);
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertEquals("Failed to add flashcard set", result.getMessages().get(0));
    }

}
