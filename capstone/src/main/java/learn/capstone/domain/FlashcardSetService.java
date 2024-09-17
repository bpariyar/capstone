package learn.capstone.domain;

import learn.capstone.data.FlashcardSetRepository;
import org.springframework.stereotype.Service;

@Service
public class FlashcardSetService {
    private final FlashcardSetRepository flashcardSetRepository;

    public FlashcardSetService(FlashcardSetRepository flashcardSetRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
    }

    //TODO: find all flashcard sets

    //TODO: add a flashcard set

    //TODO: update a flashcard set

    //TODO: Delete a flashcard set
}
