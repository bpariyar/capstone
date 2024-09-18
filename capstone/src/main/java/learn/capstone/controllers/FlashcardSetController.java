package learn.capstone.controllers;

import learn.capstone.domain.FlashcardSetService;
import learn.capstone.models.FlashcardSet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flashcard/set")
public class FlashcardSetController {
    private final FlashcardSetService service;

    public FlashcardSetController(FlashcardSetService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<FlashcardSet> findAll() {
        return service.findAll();
    }

    @GetMapping("/{flashcardSetId}")
    public ResponseEntity<FlashcardSet> findByFlashcardSetId(@PathVariable int flashcardSetId) {
        FlashcardSet flashcardSet = service.findByFlashcardSetId(flashcardSetId);
        if (flashcardSet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flashcardSet);
    }
}
