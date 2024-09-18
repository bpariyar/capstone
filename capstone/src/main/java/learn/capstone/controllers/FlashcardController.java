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
    public ResponseEntity<Flashcard> findByFlashcardId(@PathVariable int flashcardId) {
        Flashcard flashcard = service.findByFlashcardId(flashcardId);
        if (flashcard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flashcard);
    }

    @GetMapping("/cards/{flashcardSetId}")
    public List<Flashcard> findByFlashcardSetId(@PathVariable int flashcardSetId) {
       return service.findByFlashcardSetId(flashcardSetId);
    }

    @PostMapping("/add")
    public ResponseEntity<Flashcard> add(@RequestBody Flashcard flashcard) {
        Result<Flashcard> result = service.add(flashcard);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    @PutMapping("/update/{flashcardId}")
    public ResponseEntity<Void> update(@PathVariable int flashcardId, @RequestBody Flashcard flashcard) {
        if (flashcard != null && flashcard.getFlashcardId() != flashcardId) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Flashcard> result = service.update(flashcard);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
    }

    @DeleteMapping("/delete/{flashcardId}")
    public ResponseEntity<Void> deleteByFlashcardId(@PathVariable int flashcardId) {
        Result<Flashcard> result = service.deleteByFlashcardId(flashcardId);
        return new ResponseEntity<>(getStatus(result, HttpStatus.NO_CONTENT));
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
