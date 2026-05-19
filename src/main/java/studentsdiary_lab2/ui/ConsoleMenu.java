package studentsdiary_lab2.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.command.Command;
import studentsdiary_lab2.util.InputReader;

@Component
@Scope("singleton")
public class ConsoleMenu {

    private final List<Command> commands;
    private final InputReader input;

    public ConsoleMenu(List<Command> commandList, InputReader input) {
        this.commands = sortByName(commandList);
        this.input = input;
    }

    public void run() {
        printGreeting();
        while (true) {
            printMenu();
            String choice = input.readLine("Выбор: ");
            if (choice.equals("0") || choice.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                return;
            }
            Command command = pick(choice);
            if (command == null) {
                System.out.println("Выбран неизвестный пункт меню! Введите другое значение");
                continue;
            }
            System.out.println();
            try {
                command.execute();
            } catch (RuntimeException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printGreeting() {
        System.out.println("Ежедневник студента");
    }

    private void printMenu() {
        System.out.println();
        for (int i = 0; i < commands.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + commands.get(i).description());
        }
        System.out.println(" 0. Выход");
    }

    private Command pick(String choice) {
        int number;
        try {
            number = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return null;
        }
        if (number < 1 || number > commands.size()) {
            return null;
        }
        return commands.get(number - 1);
    }

    private static List<Command> sortByName(List<Command> source) {
        List<Command> copy = new ArrayList<>(source);
        copy.sort((a, b) -> a.description().compareToIgnoreCase(b.description()));
        return copy;
    }
}