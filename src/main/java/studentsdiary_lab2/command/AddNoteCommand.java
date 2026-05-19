package studentsdiary_lab2.command;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.factory.EntryFactory;
import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.util.InputReader;

@Component
public class AddNoteCommand implements Command {

    private final EntryRepository repository;
    private final EntryFactory factory;
    private final InputReader input;

    public AddNoteCommand(EntryRepository repository, EntryFactory factory, InputReader input) {
        this.repository = repository;
        this.factory = factory;
        this.input = input;
    }

    @Override
    public String description() {
        return "Добавить заметку";
    }

    @Override
    public void execute() {
        String groupId = input.readLine("Id группы (Enter - в корень): ").trim();

        EntryGroup target = null;
        if (!groupId.isEmpty()) {
            try {
                target = repository.findGroupById(groupId);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                return;
            }
            if (target == null) {
                System.out.println("Группа с таким id не найдена.");
                return;
            }
        }

        String title = input.readLine("Заголовок: ");
        String text = input.readLine("Текст: ");

        DiaryEntry note = factory.createNote(title, text);

        if (target == null) {
            repository.add(note);
        } else {
            repository.addToGroup(target, note);
        }
    }
}