package learn.capstone.data;

import learn.capstone.models.Flashcard;
import learn.capstone.models.FlashcardSet;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlashcardMapper implements RowMapper<Flashcard> {

    @Override
    public Flashcard mapRow(ResultSet resultSet, int i) throws SQLException {

        Flashcard flashcard = new Flashcard();
        flashcard.setFlashcardId(resultSet.getInt("flashcard_id"));
        flashcard.setFrontData(resultSet.getString("front_data"));
        flashcard.setBackData(resultSet.getString("back_data"));

        FlashcardSet flashcardSet = new FlashcardSet();
        flashcardSet.setFlashcardSetId(resultSet.getInt("flashcard_set_id"));
        flashcardSet.setTitle(resultSet.getString("title"));

        flashcard.setFlashcardSet(flashcardSet);
        return flashcard;
    }
}