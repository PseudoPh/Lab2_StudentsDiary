package studentsdiary_lab2.reminder;

import java.time.LocalDateTime;

import studentsdiary_lab2.model.EventEntry;

public interface ReminderStrategy {
    boolean shouldRemind(EventEntry event, LocalDateTime now);
}