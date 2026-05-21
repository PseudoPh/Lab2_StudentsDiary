package studentsdiary_lab2.command;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.factory.EntryFactory;
import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.util.InputReader;

@Component
public class AddGroupCommand implements Command {

    private final EntryRepository repository;
    private final EntryFactory factory;
    private final InputReader input;

    public AddGroupCommand(EntryRepository repository, EntryFactory factory, InputReader input) {
        this.repository = repository;
        this.factory = factory;
        this.input = input;
    }

    @Override
    public String description() {
        return "Добавить группу";
    }

    @Override
    public void execute() {
        String parentId = input.readLine("Id родительской группы (Enter - в корень): ").trim();

        EntryGroup parent = null;
        if (!parentId.isEmpty()) {
            try {
                parent = repository.findGroupById(parentId);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                return;
            }
            if (parent == null) {
                System.out.println("Группа с таким id не найдена.");
                return;
            }
        }

        String title = input.readLine("Название группы: ");
        DiaryEntry group = factory.createGroup(title);

        if (parent == null) {
            repository.add(group);
        } else {
            repository.addToGroup(parent, group);
        }
    }
}