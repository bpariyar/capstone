package learn.capstone.data;

import learn.capstone.models.Flashcard;
import learn.capstone.models.FlashcardSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class FlashcardSetJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    FlashcardSetJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindByFlashcardSetId() {
        FlashcardSet flashcardSet1 = repository.findByFlashcardSetId(1);
        assertEquals(flashcardSet1.getFlashcardSetId(), 1);
        assertEquals(flashcardSet1.getTitle(), "World History");
    }

    @Test
    void shouldFindAll() {
        List<FlashcardSet> sets = repository.findAll();
        assertTrue(sets.size() >= 1);
    }

    @Test
    void shouldAddFlashcardSet() {
        FlashcardSet flashcardSet = new FlashcardSet(0, "Test");
        FlashcardSet expected =  new FlashcardSet(3, "Test");

        FlashcardSet actual = repository.add(flashcardSet);
        assertEquals(actual, expected);
    }

    @Test
    void shouldNotAddInvalid() {
        FlashcardSet flashcardSet = new FlashcardSet(100, "");
        repository.add(flashcardSet);
        assertEquals(repository.findByFlashcardSetId(100), null);
    }

    @Test
    void shouldUpdateFlashcardSet1() {
        FlashcardSet flashcardSet = repository.findByFlashcardSetId(1);
        flashcardSet.setTitle("Test Title");
        assertTrue(repository.update(flashcardSet));
        FlashcardSet updated = repository.findByFlashcardSetId(1);
        assertEquals("Test Title", updated.getTitle());
    }

    @Test
    void shouldNotUpdateMissing() {
        FlashcardSet flashcardSet = new FlashcardSet(100, "Test");
        assertFalse(repository.update(flashcardSet));
    }

    @Test
    void shouldDeleteFlashcardSet1() {
        assertTrue(repository.deleteByFlashcardSetId(1));
        assertFalse(repository.deleteByFlashcardSetId(1));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteByFlashcardSetId(100));
    }

}
