package learn.capstone.data;

import learn.capstone.models.Flashcard;

public interface FlashcardRepository {
    Flashcard findById(int flashcardId);
}
