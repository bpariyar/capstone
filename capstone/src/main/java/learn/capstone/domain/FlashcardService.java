package learn.capstone.domain;

import learn.capstone.data.FlashcardRepository;
import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.Flashcard;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Flashcard> findByFlashcardSetId(int flashcardSetId) {
        return flashcardRepository.findByFlashcardSetId(flashcardSetId);
    }

    public Result<Flashcard> add(Flashcard flashcard) {
        Result<Flashcard> result = validate(flashcard);
        if (result.getStatus() != Status.SUCCESS) {
            return result;
        }

        Flashcard inserted = flashcardRepository.add(flashcard);
        if (inserted == null) {
            result.addMessage(Status.INVALID, "Failed to add flashcard");
        } else {
            result.setPayload(inserted);
        }

        return result;
    }

    //TODO: more in this validation
    private Result<Flashcard> validate(Flashcard flashcard) {
        Result<Flashcard> result = new Result<>();

        if (flashcard == null) {
            result.addMessage(Status.INVALID, "Flashcard cannot be null.");
            return result;
        }

        return result;

    }

    //TODO: Add flashcard to flashcard set

    //TODO: Update flashcard

    //TODO: Delete a flashcard
}
