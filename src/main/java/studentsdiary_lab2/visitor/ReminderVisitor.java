package studentsdiary_lab2.visitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EntryVisitor;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;
import studentsdiary_lab2.reminder.ReminderStrategy;

@Component
@Scope("prototype")
public class ReminderVisitor implements EntryVisitor {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private ReminderStrategy strategy;
    private LocalDateTime now;
    private int reminders = 0;

    public void init(ReminderStrategy strategy, LocalDateTime now) {
        this.strategy = strategy;
        this.now = now;
    }

    public int getReminders() {
        return reminders;
    }

    @Override
    public void visit(NoteEntry note) {
    }

    @Override
    public void visit(EventEntry event) {
        if (strategy.shouldRemind(event, now)) {
            System.out.println("Напоминание: " + event.getTitle()
                    + " в " + event.getEventAt().format(FMT));
            reminders++;
        }
    }

    @Override
    public void visit(EntryGroup group) {
        for (var child : group.getChildren()) {
            child.accept(this);
        }
    }
}