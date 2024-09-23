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
public class FlashcardJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    FlashcardJdbcTemplateRepository repository;

    private static FlashcardSet SET = new FlashcardSet(1, "Set 1");

    @BeforeEach
    void setup() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void shouldFindByFlashcardId() {
        Flashcard flashcard1 = repository.findByFlashcardId(1);
        assertEquals(flashcard1.getFlashcardId(), 1);
        assertEquals(flashcard1.getBackData(), "1492");
    }

    @Test
    void shouldFindByFlashcardSetId() {
        List<Flashcard> flashcards = repository.findByFlashcardSetId(1);
        assertTrue(flashcards.size() == 5);
    }

    @Test
    void shouldAddFlashcard() {
        Flashcard flashcard = new Flashcard(0, SET, "Test Front", "Test Back");
        Flashcard expected =  new Flashcard(11, SET, "Test Front", "Test Back");

        Flashcard actual = repository.add(flashcard);
        assertEquals(actual, expected);
    }

    @Test
    void shouldNotAddInvalid() {
        Flashcard flashcard = new Flashcard(100, SET, " ", "Test Back");
        repository.add(flashcard);
        assertEquals(repository.findByFlashcardId(100), null);
    }

    @Test
    void shouldUpdateFlashcard6() {
        Flashcard pink = repository.findByFlashcardId(6);
        pink.setFrontData("Pink");
        pink.setBackData("Rosa");
        assertTrue(repository.update(pink));
        Flashcard updated = repository.findByFlashcardId(6);
        assertEquals("Pink", updated.getFrontData());
    }

    @Test
    void shouldNotUpdateMissing() {
        Flashcard flashcard = new Flashcard(100, SET, "Front", "back");
        assertFalse(repository.update(flashcard));
    }

    @Test
    void shouldDeleteFlashcard7() {
        assertTrue(repository.deleteByFlashcardId(7));
        assertFalse(repository.deleteByFlashcardId(7));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteByFlashcardId(100));
    }

}
