package learn.capstone.data;

import learn.capstone.models.FlashcardSet;

import java.util.List;

public interface FlashcardSetRepository {
    List<FlashcardSet> findAll();

    FlashcardSet findByFlashcardSetId(int flashcardSetId);

    FlashcardSet add(FlashcardSet flashcardSet);

    boolean deleteByFlashcardSetId(int flashcardSetId);

    boolean update(FlashcardSet flashcardSet);
}
