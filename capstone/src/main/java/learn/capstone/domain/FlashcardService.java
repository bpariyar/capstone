package learn.capstone.domain;

import learn.capstone.data.FlashcardRepository;
import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.Flashcard;
import org.springframework.stereotype.Service;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final FlashcardSetRepository flashcardSetRepository;

    public FlashcardService(FlashcardRepository flashcardRepository, FlashcardSetRepository flashcardSetRepository) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardSetRepository = flashcardSetRepository;
    }

    public Flashcard findById(int flashcardId) {
        return flashcardRepository.findById(flashcardId);
    }

    //TODO: find by flashcard set id (?)

    //TODO: Add flashcard to flashcard set

    //TODO: Update flashcard

    //TODO: Delete a flashcard
}
