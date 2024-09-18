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

    //TODO: add a flashcard set

    //TODO: update a flashcard set

    //TODO: Delete a flashcard set
}
