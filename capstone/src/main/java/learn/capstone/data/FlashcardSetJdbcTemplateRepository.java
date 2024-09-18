package learn.capstone.data;

import learn.capstone.models.FlashcardSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FlashcardSetJdbcTemplateRepository implements FlashcardSetRepository {
    private final JdbcTemplate jdbcTemplate;

    public FlashcardSetJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FlashcardSet> findAll() {
        final String sql = "select flashcard_set_id, title from flashcard_set order by title;";
        return jdbcTemplate.query(sql, new FlashcardSetMapper());
    }
}
