package studentsdiary_lab2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntryGroup extends DiaryEntry {

    private final List<DiaryEntry> children = new ArrayList<>();

    public EntryGroup(String title, LocalDateTime createdAt) {
        super(title, createdAt);
    }

    public void add(DiaryEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Запись не задана");
        }
        children.add(entry);
    }

    public boolean remove(DiaryEntry entry) {
        return children.remove(entry);
    }

    @Override
    public List<DiaryEntry> getChildren() {
        return children;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public boolean removeChild(DiaryEntry entry) {
        return remove(entry);
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
}