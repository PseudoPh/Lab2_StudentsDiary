package studentsdiary_lab2.reminder;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.EventEntry;

@Component
@Scope("singleton")
@Profile("prod")
public class SilentReminderStrategy implements ReminderStrategy {
    @Override
    public boolean shouldRemind(EventEntry event, LocalDateTime now) {
        return false;
    }
}