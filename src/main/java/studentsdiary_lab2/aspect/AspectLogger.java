package studentsdiary_lab2.aspect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

final class AspectLogger {

    private static final Path LOG_FILE = Paths.get("app.log");
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private AspectLogger() {
    }

    static void write(String message) {
        String line = LocalDateTime.now().format(TS) + " " + message;
        try {
            Files.write(LOG_FILE, List.of(line),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
        }
    }
}