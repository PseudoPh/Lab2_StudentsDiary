package studentsdiary_lab2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import studentsdiary_lab2.config.AppConfig;
import studentsdiary_lab2.startup.StartupRunner;
import studentsdiary_lab2.ui.ConsoleMenu;

public final class StudentsDiaryMain {

    private StudentsDiaryMain() {}

    public static void main(String[] args) {
        String profile = resolveProfile(args);

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.getEnvironment().setActiveProfiles(profile);
            context.register(AppConfig.class);
            context.refresh();

            context.getBean(StartupRunner.class).run();
            context.getBean(ConsoleMenu.class).run();
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