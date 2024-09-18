package learn.capstone.data;

import learn.capstone.models.FlashcardSet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlashcardSetMapper implements RowMapper<FlashcardSet> {

    @Override
    public FlashcardSet mapRow(ResultSet resultSet, int i) throws SQLException {
        FlashcardSet flashcardSet = new FlashcardSet();
        flashcardSet.setFlashcardSetId(resultSet.getInt("flashcard_set_id"));
        flashcardSet.setTitle(resultSet.getString("title"));
        return flashcardSet;
    }
}
