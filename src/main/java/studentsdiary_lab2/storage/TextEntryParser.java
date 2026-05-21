package studentsdiary_lab2.storage;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.Address;
import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EntryMarkers;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;
import studentsdiary_lab2.visitor.SerializingVisitor;

@Component
public class TextEntryParser {

    public List<DiaryEntry> parse(List<String> lines) {
        List<DiaryEntry> roots = new ArrayList<>();
        Deque<EntryGroup> stack = new ArrayDeque<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) continue;
            String[] parts = split(line);
            String type = parts[0];

            switch (type) {
                case EntryMarkers.NOTE:
                    add(stack, roots, new NoteEntry(
                            parts[2],
                            parseDate(parts[3]),
                            parts[4]));
                    break;
                case EntryMarkers.EVENT:
                    add(stack, roots, new EventEntry(
                            parts[2],
                            parseDate(parts[3]),
                            parseDate(parts[4]),
                            parseDate(parts[5]),
                            new Address(parts[6], parts[7], parts[8])));
                    break;
                case EntryMarkers.GROUP_START:
                    EntryGroup g = new EntryGroup(parts[2], parseDate(parts[3]));
                    add(stack, roots, g);
                    stack.push(g);
                    break;
                case EntryMarkers.GROUP_END:
                    if (stack.isEmpty()) {
                        throw new IllegalStateException("Группа закрыта без открытия");
                    }
                    stack.pop();
                    break;
                default:
                    throw new IllegalStateException("Неизвестный тип записи: " + type);
            }
        }

        if (!stack.isEmpty()) {
            throw new IllegalStateException("Не все группы закрыты в файле");
        }
        return roots;
    }

    private static LocalDateTime parseDate(String value) {
        return LocalDateTime.parse(value, SerializingVisitor.FORMATTER);
    }

    private static void add(Deque<EntryGroup> stack, List<DiaryEntry> roots, DiaryEntry entry) {
        if (stack.isEmpty()) {
            roots.add(entry);
        } else {
            stack.peek().add(entry);
        }
    }

    private static String[] split(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\\' && i + 1 < line.length()) {
                char next = line.charAt(i + 1);
                if (next == 'p') current.append('|');
                else if (next == 'n') current.append('\n');
                else if (next == '\\') current.append('\\');
                else current.append(next);
                i++;
            } else if (c == '|') {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        parts.add(current.toString());
        return parts.toArray(new String[0]);
    }
}