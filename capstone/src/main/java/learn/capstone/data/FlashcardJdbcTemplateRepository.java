package learn.capstone.data;

import learn.capstone.models.Flashcard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FlashcardJdbcTemplateRepository implements FlashcardRepository {
    private final JdbcTemplate jdbcTemplate;

    public FlashcardJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Flashcard findById(int flashcardId) {
        final String sql = "select f.flashcard_id, f.front_data, f.back_data, " +
                "fs.title, fs.flashcard_set_id "
                + "from flashcard f "
                + "inner join flashcard_set fs on f.flashcard_set_id = fs.flashcard_set_id "
                + "where flashcard_id = ?;";

        return jdbcTemplate.query(sql, new FlashcardMapper(), flashcardId)
                .stream().findFirst().orElse(null);
    }
}
