package studentsdiary_lab2.visitor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EntryVisitor;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SerializingVisitor implements EntryVisitor {

    public static final String SEP = "|";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final List<String> lines = new ArrayList<>();

    public List<String> getLines() {
        return lines;
    }

    @Override
    public void visit(NoteEntry note) {
        lines.add(String.join(SEP,
                "NOTE",
                note.getId(),
                escape(note.getTitle()),
                note.getCreatedAt().format(FORMATTER),
                escape(note.getContent())));
    }

    @Override
    public void visit(EventEntry event) {
        lines.add(String.join(SEP,
                "EVENT",
                event.getId(),
                escape(event.getTitle()),
                event.getCreatedAt().format(FORMATTER),
                event.getEventAt().format(FORMATTER),
                escape(event.getAddress().city()),
                escape(event.getAddress().street()),
                escape(event.getAddress().building())));
    }

    @Override
    public void visit(EntryGroup group) {
        lines.add(String.join(SEP,
                "GROUP_START",
                group.getId(),
                escape(group.getTitle()),
                group.getCreatedAt().format(FORMATTER)));
        for (var child : group.getChildren()) {
            child.accept(this);
        }
        lines.add(String.join(SEP, "GROUP_END", group.getId()));
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("|", "\\p").replace("\n", "\\n");
    }
}