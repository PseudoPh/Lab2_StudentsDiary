package studentsdiary_lab2.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.visitor.SerializingVisitor;

@Component
public class TextDiaryStorage {

    private static final Path FILE = Paths.get("diary.txt");

    private final TextEntryParser parser;
    private final ObjectProvider<SerializingVisitor> serializingVisitors;

    public TextDiaryStorage(TextEntryParser parser,
                            ObjectProvider<SerializingVisitor> serializingVisitors) {
        this.parser = parser;
        this.serializingVisitors = serializingVisitors;
    }

    public List<DiaryEntry> load() {
        if (!Files.exists(FILE)) {
            return List.of();
        }
        try {
            List<String> lines = Files.readAllLines(FILE);
            return parser.parse(lines);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл дневника", e);
        }
    }

    public void save(List<DiaryEntry> entries) {
        SerializingVisitor visitor = serializingVisitors.getObject();
        for (DiaryEntry entry : entries) {
            entry.accept(visitor);
        }
        try {
            Files.write(FILE, visitor.getLines());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл дневника", e);
        }
    }
}