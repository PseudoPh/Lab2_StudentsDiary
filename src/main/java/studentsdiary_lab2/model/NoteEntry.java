package studentsdiary_lab2.model;

import java.time.LocalDateTime;

public final class NoteEntry extends DiaryEntry {

    private String content;

    public NoteEntry(String title, LocalDateTime createdAt, String content) {
        super(title, createdAt);
        setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
}