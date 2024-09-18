package learn.capstone.domain;

import learn.capstone.data.FlashcardSetRepository;
import learn.capstone.models.FlashcardSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardSetService {
    private final FlashcardSetRepository flashcardSetRepository;

    public FlashcardSetService(FlashcardSetRepository flashcardSetRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
    }

    public List<FlashcardSet> findAll(){
        return flashcardSetRepository.findAll();
    }

    public FlashcardSet findByFlashcardSetId(int flashcardSetId) {
        return flashcardSetRepository.findByFlashcardSetId(flashcardSetId);
    }

    public Result<FlashcardSet> add(FlashcardSet flashcardSet) {
            Result<FlashcardSet> result = validate(flashcardSet);
            if (result.getStatus() != Status.SUCCESS) {
                return result;
            }

            FlashcardSet inserted = flashcardSetRepository.add(flashcardSet);
            if (inserted == null) {
                result.addMessage(Status.INVALID, "Failed to add flashcard");
            } else {
                result.setPayload(inserted);
            }

            return result;
        }

    //TODO: more in this validation
    private Result<FlashcardSet> validate(FlashcardSet flashcardSet) {
        Result<FlashcardSet> result = new Result<>();

        if (flashcardSet == null) {
            result.addMessage(Status.INVALID, "Flashcard set cannot be null.");
            return result;
        }

        return result;
    }

    public Result<FlashcardSet> deleteByFlashcardSetId(int flashcardSetId) {
        Result<FlashcardSet> result = new Result<>();
        boolean deleted = flashcardSetRepository.deleteByFlashcardSetId(flashcardSetId);
        if (!deleted) {
            result.addMessage(Status.NOT_FOUND, "Flashcard set id #" + flashcardSetId + " not found.");
        }
        return result;
    }
    //TODO: update a flashcard set

    }


