package learn.capstone.models;

import java.util.Objects;

public class FlashcardSet {
    private int flashcardSetId;
    private String title;

    public FlashcardSet() {}

    public FlashcardSet(int flashcardSetId, String title) {
        this.flashcardSetId = flashcardSetId;
        this.title = title;
    }

    public int getFlashcardSetId() {
        return flashcardSetId;
    }

    public void setFlashcardSetId(int flashcardSetId) {
        this.flashcardSetId = flashcardSetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlashcardSet that = (FlashcardSet) o;
        return flashcardSetId == that.flashcardSetId && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flashcardSetId, title);
    }
}
