package learn.capstone.models;

import java.util.Objects;

public class Flashcard {
    private int flashcardId;
    private FlashcardSet flashcardSet;
    private String frontData;
    private String backData;

    public Flashcard() {}

    public Flashcard(int flashcardId, FlashcardSet flashcardSet, String frontData, String backData) {
        this.flashcardId = flashcardId;
        this.flashcardSet = flashcardSet;
        this.frontData = frontData;
        this.backData = backData;
    }

    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }

    public FlashcardSet getFlashcardSet() {
        return flashcardSet;
    }

    public void setFlashcardSet(FlashcardSet flashcardSet) {
        this.flashcardSet = flashcardSet;
    }

    public String getFrontData() {
        return frontData;
    }

    public void setFrontData(String frontData) {
        this.frontData = frontData;
    }

    public String getBackData() {
        return backData;
    }

    public void setBackData(String backData) {
        this.backData = backData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return flashcardId == flashcard.flashcardId && Objects.equals(flashcardSet, flashcard.flashcardSet) && Objects.equals(frontData, flashcard.frontData) && Objects.equals(backData, flashcard.backData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flashcardId, flashcardSet, frontData, backData);
    }
}
