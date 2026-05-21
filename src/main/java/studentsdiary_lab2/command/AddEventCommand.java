package studentsdiary_lab2.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.factory.EntryFactory;
import studentsdiary_lab2.model.Address;
import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.repository.EntryRepository;
import studentsdiary_lab2.util.InputReader;

@Component
public class AddEventCommand implements Command {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final EntryRepository repository;
    private final EntryFactory factory;
    private final InputReader input;

    public AddEventCommand(EntryRepository repository, EntryFactory factory, InputReader input) {
        this.repository = repository;
        this.factory = factory;
        this.input = input;
    }

    @Override
    public String description() {
        return "Добавить событие";
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

        LocalDateTime eventAt = readDateTime("Дата/время начала - дд.ММ.гггг ЧЧ:мм ");
        if (eventAt == null) return;

        LocalDateTime eventEndAt = readDateTime("Дата/время окончания - дд.ММ.гггг ЧЧ:мм ");
        if (eventEndAt == null) return;

        if (!eventEndAt.isAfter(eventAt)) {
            System.out.println("Ошибка: окончание должно быть позже начала.");
            return;
        }

        String city = input.readLine("Город: ");
        String street = input.readLine("Улица: ");
        String building = input.readLine("Дом: ");

        DiaryEntry event = factory.createEvent(title, eventAt, eventEndAt,
                new Address(city, street, building));

        if (target == null) {
            repository.add(event);
        } else {
            repository.addToGroup(target, event);
        }
    }

    private LocalDateTime readDateTime(String prompt) {
        try {
            return LocalDateTime.parse(input.readLine(prompt), FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты. Пример: 21.05.2026 11:35");
            return null;
        }
    }
}