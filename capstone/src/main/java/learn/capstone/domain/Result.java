package learn.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private Status status = Status.SUCCESS;
    private ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public Status getStatus() {
        return status;
    }

    public T getPayload() {
        return payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void addMessage(Status status, String message) {
        this.status = status;
        messages.add(message);
    }

    public boolean isSuccess() {
        return messages.size() == 0;
    }
}
