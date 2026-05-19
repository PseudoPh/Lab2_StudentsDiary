package studentsdiary_lab2.command;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.visitor.PrintingVisitor;

@Component
public class PrintCommand implements Command {

    private final EntryRepository repository;
    private final ObjectProvider<PrintingVisitor> printingVisitors;

    public PrintCommand(EntryRepository repository,
                        ObjectProvider<PrintingVisitor> printingVisitors) {
        this.repository = repository;
        this.printingVisitors = printingVisitors;
    }

    @Override
    public String description() {
        return "Показать все записи";
    }

    @Override
    public void execute() {
        List<DiaryEntry> all = repository.findAll();
        if (all.isEmpty()) {
            System.out.println("Записей пока нет.");
            return;
        }
        PrintingVisitor visitor = printingVisitors.getObject();
        for (DiaryEntry entry : all) {
            entry.accept(visitor);
        }
    }
}