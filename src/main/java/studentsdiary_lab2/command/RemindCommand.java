package studentsdiary_lab2.command;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.reminder.ReminderStrategy;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.visitor.ReminderVisitor;

@Component
public class RemindCommand implements Command {

    private final EntryRepository repository;
    private final ReminderStrategy strategy;
    private final ApplicationContext context;

    public RemindCommand(EntryRepository repository,
                         ReminderStrategy strategy,
                         ApplicationContext context) {
        this.repository = repository;
        this.strategy = strategy;
        this.context = context;
    }

    @Override
    public String description() { return "Показать напоминания"; }

    @Override
    public void execute() {
        ReminderVisitor visitor = context.getBean(ReminderVisitor.class);
        visitor.init(strategy, LocalDateTime.now());

        for (DiaryEntry entry : repository.findAll()) entry.accept(visitor);
        if (visitor.getReminders() == 0) System.out.println("Ближайших напоминаний нет.");
    }
}