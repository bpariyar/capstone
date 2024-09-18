package learn.capstone.controllers;

import learn.capstone.domain.FlashcardService;
import learn.capstone.models.Flashcard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {
    private final FlashcardService service;

    public FlashcardController(FlashcardService service) {
        this.service = service;
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<Flashcard> findById(@PathVariable int flashcardId) {
        Flashcard flashcard = service.findById(flashcardId);
        if (flashcard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flashcard);
    }

    @GetMapping("/set/{flashcardSetId}")
    public List<Flashcard> findByFlashcardSetId(@PathVariable int flashcardSetId) {
       return service.findByFlashcardSetId(flashcardSetId);
    }

}
