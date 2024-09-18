package learn.capstone.data;

import learn.capstone.models.Flashcard;

import java.util.List;

public interface FlashcardRepository {
    Flashcard findById(int flashcardId);

    List<Flashcard> findByFlashcardSetId(int flashcardSetId);

    Flashcard add(Flashcard flashcard);
}
