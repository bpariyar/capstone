package learn.capstone.controllers;

import learn.capstone.domain.FlashcardService;
import learn.capstone.domain.Result;
import learn.capstone.models.Flashcard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Flashcard> add(@RequestBody Flashcard flashcard) {
        Result<Flashcard> result = service.add(flashcard);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    private HttpStatus getStatus(Result<Flashcard> result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }

}
