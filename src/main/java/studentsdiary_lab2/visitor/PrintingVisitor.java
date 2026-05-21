package studentsdiary_lab2.visitor;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EntryVisitor;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;

@Component
@Scope("prototype")
public class PrintingVisitor implements EntryVisitor {

    private static final DateTimeFormatter DATE_TIME_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm");

    private int indent = 0;

    @Override
    public void visit(NoteEntry note) {
        System.out.println(pad() + "[Заметка] " + note.getTitle()
                + " - " + note.getContent()
                + "  (id=" + shortId(note.getId()) + ")");
    }

    @Override
    public void visit(EventEntry event) {
        String interval;
        var start = event.getEventAt();
        var end = event.getEventEndAt();

        if (start.toLocalDate().equals(end.toLocalDate())) {
            interval = start.format(DATE_TIME_FMT) + " - " + end.format(TIME_FMT);
        } else {
            interval = start.format(DATE_TIME_FMT) + " - " + end.format(DATE_TIME_FMT);
        }

        System.out.println(pad() + "[Событие] " + event.getTitle()
                + " - " + interval
                + ", " + event.getAddress()
                + "  (id=" + shortId(event.getId()) + ")");
    }

    @Override
    public void visit(EntryGroup group) {
        System.out.println(pad() + "[Группа] " + group.getTitle()
                + "  (id=" + shortId(group.getId()) + ")");
        indent++;
        for (var child : group.getChildren()) {
            child.accept(this);
        }
        indent--;
    }

    private String pad() {
        return "  ".repeat(indent);
    }

    private static String shortId(String id) {
        return id.length() > 8 ? id.substring(0, 8) : id;
    }
}