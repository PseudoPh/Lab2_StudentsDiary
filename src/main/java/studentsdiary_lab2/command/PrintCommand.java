package studentsdiary_lab2.command;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.visitor.PrintingVisitor;

@Component
public class PrintCommand implements Command {

    private final EntryRepository repository;
    private final ApplicationContext context;

    public PrintCommand(EntryRepository repository, ApplicationContext context) {
        this.repository = repository;
        this.context = context;
    }

    @Override
    public String description() { return "Показать все записи"; }

    @Override
    public void execute() {
        List<DiaryEntry> all = repository.findAll();
        if (all.isEmpty()) {
            System.out.println("Записей пока нет.");
            return;
        }
        PrintingVisitor visitor = context.getBean(PrintingVisitor.class);
        for (DiaryEntry entry : all) entry.accept(visitor);
    }
}