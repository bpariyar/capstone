package learn.capstone.data;

import learn.capstone.models.FlashcardSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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

    @Override
    public FlashcardSet findByFlashcardSetId(int flashcardSetId) {
        final String sql = "select flashcard_set_id, title from flashcard_set where flashcard_set_id = ?;";
        return jdbcTemplate.query(sql, new FlashcardSetMapper(), flashcardSetId)
                .stream().findFirst().orElse(null);
    }

    @Override
    public FlashcardSet add(FlashcardSet flashcardSet) {
        final String sql = "insert into flashcard_set (title) values (?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, flashcardSet.getTitle());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        flashcardSet.setFlashcardSetId(keyHolder.getKey().intValue());

        return flashcardSet;
    }

    @Override
    public boolean deleteByFlashcardSetId(int flashcardSetId) {
        final String sql = "delete from flashcard_set where flashcard_set_id = ?";
        return jdbcTemplate.update(sql, flashcardSetId) > 0;
    }

    @Override
    public boolean update(FlashcardSet flashcardSet) {
        final String sql = "update flashcard_set set title = ? where flashcard_set_id = ?;";

        return jdbcTemplate.update(sql,
                flashcardSet.getTitle(),
                flashcardSet.getFlashcardSetId()) > 0;
    }
}
