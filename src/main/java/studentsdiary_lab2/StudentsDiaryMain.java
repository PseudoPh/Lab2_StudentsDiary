package studentsdiary_lab2;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import studentsdiary_lab2.command.AddEventCommand;
import studentsdiary_lab2.command.AddGroupCommand;
import studentsdiary_lab2.command.AddNoteCommand;
import studentsdiary_lab2.command.LoadCommand;
import studentsdiary_lab2.command.PrintCommand;
import studentsdiary_lab2.command.RemindCommand;
import studentsdiary_lab2.command.RemoveCommand;
import studentsdiary_lab2.command.SaveCommand;
import studentsdiary_lab2.config.AppConfig;
import studentsdiary_lab2.startup.StartupRunner;

public final class StudentsDiaryMain {

    private StudentsDiaryMain() {}

    public static void main(String[] args) {
        String profile = resolveProfile(args);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles(profile);
            context.register(AppConfig.class);
            context.refresh();

            StartupRunner startupRunner = context.getBean(StartupRunner.class);
            AddNoteCommand addNoteCommand = context.getBean(AddNoteCommand.class);
            AddEventCommand addEventCommand = context.getBean(AddEventCommand.class);
            AddGroupCommand addGroupCommand = context.getBean(AddGroupCommand.class);
            RemoveCommand removeCommand = context.getBean(RemoveCommand.class);
            PrintCommand printCommand = context.getBean(PrintCommand.class);
            RemindCommand remindCommand = context.getBean(RemindCommand.class);
            SaveCommand saveCommand = context.getBean(SaveCommand.class);
            LoadCommand loadCommand = context.getBean(LoadCommand.class);
            Scanner scanner = context.getBean(Scanner.class);

            startupRunner.run();

            boolean runMarker = true;
            while (runMarker) {
                System.out.println();
                System.out.println("1. " + addNoteCommand.description());
                System.out.println("2. " + addEventCommand.description());
                System.out.println("3. " + addGroupCommand.description());
                System.out.println("4. " + removeCommand.description());
                System.out.println("5. " + printCommand.description());
                System.out.println("6. " + remindCommand.description());
                System.out.println("7. " + saveCommand.description());
                System.out.println("8. " + loadCommand.description());
                System.out.println("0. Выход");
                System.out.print("> ");

                switch (scanner.nextLine().trim()) {
                    case "1":
                        addNoteCommand.execute();
                        break;
                    case "2":
                        addEventCommand.execute();
                        break;
                    case "3":
                        addGroupCommand.execute();
                        break;
                    case "4":
                        removeCommand.execute();
                        break;
                    case "5":
                        printCommand.execute();
                        break;
                    case "6":
                        remindCommand.execute();
                        break;
                    case "7":
                        saveCommand.execute();
                        break;
                    case "8":
                        loadCommand.execute();
                        break;
                    case "0":
                        runMarker = false;
                        break;
                    default:
                        System.out.println("Неизвестная команда.");
                }
            }
        }
    }

    private static String resolveProfile(String[] args) {
        if (args.length > 0 && !args[0].isBlank()) {
            return args[0];
        }
        String systemProfile = System.getProperty("spring.profiles.active");
        if (systemProfile != null && !systemProfile.isBlank()) {
            return systemProfile;
        }
        return "dev";
    }
}