package studentsdiary_lab2.startup;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.reminder.ReminderStrategy;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.storage.TextDiaryStorage;
import studentsdiary_lab2.visitor.ReminderVisitor;

@Component
@Scope("singleton")
public class StartupRunner {

    private final EntryRepository repository;
    private final TextDiaryStorage storage;
    private final ReminderStrategy reminderStrategy;
    private final ApplicationContext context;

    public StartupRunner(EntryRepository repository,
                         TextDiaryStorage storage,
                         ReminderStrategy reminderStrategy,
                         ApplicationContext context) {
        this.repository = repository;
        this.storage = storage;
        this.reminderStrategy = reminderStrategy;
        this.context = context;
    }

    public void run() {
        loadFromFile();
        showReminders();
    }

    private void loadFromFile() {
        try {
            List<DiaryEntry> loaded = storage.load();
            if (loaded.isEmpty()) {
                System.out.println("Файл дневника пуст или не найден.");
                return;
            }
            repository.replaceAll(loaded);
            System.out.println("Загружено записей: " + loaded.size());
        } catch (RuntimeException e) {
            System.out.println("Не удалось загрузить дневник: " + e.getMessage());
        }
    }

    private void showReminders() {
        List<DiaryEntry> entries = repository.findAll();
        if (entries.isEmpty()) return;

        ReminderVisitor visitor = context.getBean(ReminderVisitor.class);
        visitor.init(reminderStrategy, LocalDateTime.now());

        for (DiaryEntry entry : entries) entry.accept(visitor);
        if (visitor.getReminders() == 0) System.out.println("Ближайших событий нет.");
    }
}