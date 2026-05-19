package studentsdiary_lab2.model;

public interface EntryVisitor {
    void visit(NoteEntry note);
    void visit(EventEntry event);
    void visit(EntryGroup group);
}