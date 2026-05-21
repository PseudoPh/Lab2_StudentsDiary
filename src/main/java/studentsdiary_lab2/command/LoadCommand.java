package studentsdiary_lab2.command;

import java.util.List;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.storage.TextDiaryStorage;

@Component
public class LoadCommand implements Command {

    private final EntryRepository repository;
    private final TextDiaryStorage storage;

    public LoadCommand(EntryRepository repository, TextDiaryStorage storage) {
        this.repository = repository;
        this.storage = storage;
    }

    @Override
    public String description() {
        return "Загрузить дневник из файла";
    }

    @Override
    public void execute() {
        List<DiaryEntry> loaded = storage.load();
        repository.replaceAll(loaded);
        System.out.println("Загружено записей: " + loaded.size());
    }
}