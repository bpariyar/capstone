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

    public List<FlashcardSet> findAll() {
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

    public Result<FlashcardSet> deleteByFlashcardSetId(int flashcardSetId) {
        Result<FlashcardSet> result = new Result<>();
        boolean deleted = flashcardSetRepository.deleteByFlashcardSetId(flashcardSetId);
        if (!deleted) {
            result.addMessage(Status.NOT_FOUND, "Flashcard set id #" + flashcardSetId + " not found.");
        }
        return result;
    }

    public Result<FlashcardSet> update(FlashcardSet flashcardSet) {
        Result<FlashcardSet> result = validate(flashcardSet);
        if (result.getStatus() != Status.SUCCESS) {
            return result;
        }

        if (!flashcardSetRepository.update(flashcardSet)) {
            result.addMessage(Status.NOT_FOUND, "Flashcard set id #" + flashcardSet.getFlashcardSetId() + " not found.");
        }

        return result;
    }

    private Result<FlashcardSet> validate(FlashcardSet flashcardSet) {
        Result<FlashcardSet> result = new Result<>();
        List<FlashcardSet> all = findAll();

        if (flashcardSet == null) {
            result.addMessage(Status.INVALID, "Flashcard set cannot be null.");
            return result;
        }

        for(FlashcardSet fs : all) {
            if(fs.getTitle().equals(flashcardSet.getTitle())) {
                result.addMessage(Status.INVALID, "Flashcard set already exists with that name.");
                return result;
            }
        }

        if(isEmptyOrNull(flashcardSet.getTitle())) {
            result.addMessage(Status.INVALID, "Flashcard set title must have an input.");
            return result;
        }

        return result;
    }

    private boolean isEmptyOrNull(String input) {
        if(input == null) {
            return true;
        }
        if(input.isEmpty() || input.isBlank()) {
            return true;
        }
        return false;
    }

}


