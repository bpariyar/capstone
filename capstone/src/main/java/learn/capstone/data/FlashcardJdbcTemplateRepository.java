package learn.capstone.data;

import learn.capstone.models.Flashcard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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

    @Override
    public List<Flashcard> findByFlashcardSetId(int flashcardSetId) {
        final String sql = "select f.flashcard_id, f.front_data, f.back_data, " +
                "fs.title, fs.flashcard_set_id "
                + "from flashcard f "
                + "inner join flashcard_set fs on f.flashcard_set_id = fs.flashcard_set_id "
                + "where fs.flashcard_set_id = ?;";

        return jdbcTemplate.query(sql, new FlashcardMapper(), flashcardSetId);
    }

    @Override
    public Flashcard add(Flashcard flashcard) {
        final String sql = "insert into flashcard "
                + "(flashcard_set_id, front_data, back_data) "
                + "values (?,?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, flashcard.getFlashcardSet().getFlashcardSetId());
            ps.setString(2, flashcard.getFrontData());
            ps.setString(3, flashcard.getBackData());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        flashcard.setFlashcardId(keyHolder.getKey().intValue());

        return flashcard;
    }


}
