package studentsdiary_lab2.repository;

import org.springframework.stereotype.Repository;

import studentsdiary_lab2.model.DiaryEntry;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.observer.EntryObserver;
import studentsdiary_lab2.storage.TextDiaryStorage;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EntryRepository {

    private final TextDiaryStorage storage;
    private final List<EntryObserver> observers;
    private final List<DiaryEntry> entries = new ArrayList<>();

    public EntryRepository(TextDiaryStorage storage, List<EntryObserver> observers) {
        this.storage = storage;
        this.observers = observers;
    }

    public List<DiaryEntry> findAll() {
        return entries;
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
            if (e.isGroup() && removeFromGroup((EntryGroup) e, target)) {
                return true;
            }
        }
        return false;
    }

    private boolean removeFromGroup(EntryGroup group, DiaryEntry target) {
        if (group.removeChild(target)) {
            return true;
        }
        for (DiaryEntry child : group.getChildren()) {
            if (child.isGroup() && removeFromGroup((EntryGroup) child, target)) {
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
        List<EntryGroup> found = new ArrayList<>();
        findGroups(entries, id, found);
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() > 1) {
            throw new IllegalArgumentException("Найдено несколько групп с таким id, уточните");
        }
        return found.get(0);
    }

    private void findGroups(List<DiaryEntry> source, String prefix, List<EntryGroup> out) {
        for (DiaryEntry e : source) {
            if (e.isGroup()) {
                EntryGroup g = (EntryGroup) e;
                if (g.getId().startsWith(prefix)) {
                    out.add(g);
                }
                findGroups(g.getChildren(), prefix, out);
            }
        }
    }

    public void save() {
        storage.save(entries);
    }
}