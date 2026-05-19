package studentsdiary_lab2.command;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.util.InputReader;

@Component
public class RemoveCommand implements Command {

    private final EntryRepository repository;
    private final InputReader input;

    public RemoveCommand(EntryRepository repository, InputReader input) {
        this.repository = repository;
        this.input = input;
    }

    @Override
    public String description() {
        return "Удалить запись по id";
    }

    @Override
    public void execute() {
        String id = input.readLine("Id записи (можно префикс): ").trim();
        if (id.isEmpty()) {
            System.out.println("Id не задан.");
            return;
        }

        try {
            boolean ok = repository.removeById(id);
            if (ok) {
                System.out.println("Удалено.");
            } else {
                System.out.println("Запись с таким id не найдена.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}