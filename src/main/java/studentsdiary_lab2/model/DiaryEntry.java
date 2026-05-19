package studentsdiary_lab2.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class DiaryEntry {

    private final String id;
    private String title;
    private LocalDateTime createdAt;

    protected DiaryEntry(String title, LocalDateTime createdAt) {
        this.id = UUID.randomUUID().toString();
        setTitle(title);
        setCreatedAt(createdAt);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Заголовок не задан");
        }
        this.title = title;
    }

    public boolean isGroup() {
        return false;
    }

    public boolean removeChild(DiaryEntry entry) {
        return false;
    }

    public List<DiaryEntry> getChildren() {
        return Collections.emptyList();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("Дата создания не задана");
        }
        this.createdAt = createdAt;
    }

    public abstract void accept(EntryVisitor visitor);

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        DiaryEntry that = (DiaryEntry) other;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}