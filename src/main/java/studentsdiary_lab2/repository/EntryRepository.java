package studentsdiary_lab2.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.observer.EntryObserver;
import studentsdiary_lab2.storage.TextDiaryStorage;
import studentsdiary_lab2.visitor.GroupFinderVisitor;

@Component
public class EntryRepository {

    private final TextDiaryStorage storage;
    private final List<EntryObserver> observers;
    private final ApplicationContext context;
    private final List<DiaryEntry> entries = new ArrayList<>();

    public EntryRepository(TextDiaryStorage storage,
                           List<EntryObserver> observers,
                           ApplicationContext context) {
        this.storage = storage;
        this.observers = observers;
        this.context = context;
    }

    public List<DiaryEntry> findAll() {
        return Collections.unmodifiableList(entries);
    }

    public void replaceAll(List<DiaryEntry> newEntries) {
        entries.clear();
        if (newEntries != null) {
            entries.addAll(newEntries);
        }
    }

    public void add(DiaryEntry entry) {
        entries.add(entry);
        for (EntryObserver o : observers) {
            o.onAdded(entry);
        }
    }

    public void addToGroup(EntryGroup group, DiaryEntry entry) {
        group.add(entry);
        for (EntryObserver o : observers) {
            o.onAdded(entry);
        }
    }

    public boolean removeById(String id) {
        DiaryEntry target = findById(id);
        if (target == null) {
            return false;
        }
        boolean removed = removeFromAny(target);
        if (removed) {
            for (EntryObserver o : observers) {
                o.onRemoved(target);
            }
        }
        return removed;
    }

    private boolean removeFromAny(DiaryEntry target) {
        if (entries.remove(target)) {
            return true;
        }
        for (DiaryEntry e : entries) {
            if (e.removeDescendant(target)) {
                return true;
            }
        }
        return false;
    }

    public DiaryEntry findById(String id) {
        List<DiaryEntry> found = new ArrayList<>();
        findAll(entries, id, found);
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() > 1) {
            throw new IllegalArgumentException("Найдено несколько записей с таким id, уточните");
        }
        return found.get(0);
    }

    private void findAll(List<DiaryEntry> source, String prefix, List<DiaryEntry> out) {
        for (DiaryEntry e : source) {
            if (e.getId().startsWith(prefix)) {
                out.add(e);
            }
            findAll(e.getChildren(), prefix, out);
        }
    }

    public EntryGroup findGroupById(String id) {
        GroupFinderVisitor finder = context.getBean(GroupFinderVisitor.class);
        finder.init(id);
        for (DiaryEntry e : entries) {
            e.accept(finder);
        }
        List<EntryGroup> found = finder.getFound();
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() > 1) {
            throw new IllegalArgumentException("Найдено несколько групп с таким id, уточните");
        }
        return found.get(0);
    }

    public void save() {
        storage.save(entries);
    }
}