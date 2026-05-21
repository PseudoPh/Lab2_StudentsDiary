package studentsdiary_lab2.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.visitor.SerializingVisitor;

@Component
public class TextDiaryStorage {

    private static final Path FILE = Paths.get("diary.txt");

    private final TextEntryParser parser;
    private final ApplicationContext context;

    public TextDiaryStorage(TextEntryParser parser, ApplicationContext context) {
        this.parser = parser;
        this.context = context;
    }

    public List<DiaryEntry> load() {
        if (!Files.exists(FILE)) return List.of();
        try {
            return parser.parse(Files.readAllLines(FILE));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл дневника", e);
        }
    }

    public void save(List<DiaryEntry> entries) {
        SerializingVisitor visitor = context.getBean(SerializingVisitor.class);
        for (DiaryEntry entry : entries) entry.accept(visitor);
        try {
            Files.write(FILE, visitor.getLines());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл дневника", e);
        }
    }
}