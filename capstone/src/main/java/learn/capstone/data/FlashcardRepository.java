package learn.capstone.data;

import learn.capstone.models.Flashcard;

import java.util.List;

public interface FlashcardRepository {
    Flashcard findByFlashcardId(int flashcardId);

    List<Flashcard> findByFlashcardSetId(int flashcardSetId);

    Flashcard add(Flashcard flashcard);

    boolean update(Flashcard flashcard);

    boolean deleteByFlashcardId(int flashcardId);
}
