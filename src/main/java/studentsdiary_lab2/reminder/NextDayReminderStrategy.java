package studentsdiary_lab2.reminder;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.EventEntry;

@Component
@Scope("singleton")
@Profile({"dev", "default"})
public class NextDayReminderStrategy implements ReminderStrategy {

    @Override
    public boolean shouldRemind(EventEntry event, LocalDateTime now) {
        Duration delta = Duration.between(now, event.getEventAt());
        return !delta.isNegative() && delta.toHours() <= 24;
    }
}