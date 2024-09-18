package learn.capstone.data;

import learn.capstone.models.FlashcardSet;

import java.util.List;

public interface FlashcardSetRepository {
    List<FlashcardSet> findAll();

    FlashcardSet findByFlashcardSetId(int flashcardSetId);
}
