package learn.capstone.controllers;

import learn.capstone.domain.FlashcardSetService;
import learn.capstone.domain.Result;
import learn.capstone.models.FlashcardSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<FlashcardSet> add(@RequestBody FlashcardSet flashcardSet) {
        Result<FlashcardSet> result = service.add(flashcardSet);
        return new ResponseEntity<>(result.getPayload(), getStatus(result, HttpStatus.CREATED));
    }

    private HttpStatus getStatus(Result<FlashcardSet> result, HttpStatus statusDefault) {
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
