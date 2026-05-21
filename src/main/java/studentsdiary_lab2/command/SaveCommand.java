package studentsdiary_lab2.command;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.storage.TextDiaryStorage;

@Component
public class SaveCommand implements Command {

    private final EntryRepository repository;
    private final TextDiaryStorage storage;

    public SaveCommand(EntryRepository repository, TextDiaryStorage storage) {
        this.repository = repository;
        this.storage = storage;
    }

    @Override
    public String description() {
        return "Сохранить дневник в файл";
    }

    @Override
    public void execute() {
        storage.save(repository.findAll());
        System.out.println("Сохранено.");
    }
}