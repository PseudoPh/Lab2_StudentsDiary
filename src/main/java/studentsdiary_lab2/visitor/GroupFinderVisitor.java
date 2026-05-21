package studentsdiary_lab2.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EntryVisitor;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;

public class GroupFinderVisitor implements EntryVisitor {

    private String prefix = "";
    private final List<EntryGroup> found = new ArrayList<>();

    public void init(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    public List<EntryGroup> getFound() {
        return Collections.unmodifiableList(found);
    }

    @Override
    public void visit(NoteEntry note) {
    }

    @Override
    public void visit(EventEntry event) {
    }

    @Override
    public void visit(EntryGroup group) {
        if (group.getId().startsWith(prefix)) {
            found.add(group);
        }
        for (DiaryEntry child : group.getChildren()) {
            child.accept(this);
        }
    }
}