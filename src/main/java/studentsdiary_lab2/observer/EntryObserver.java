package studentsdiary_lab2.observer;

import studentsdiary_lab2.model.DiaryEntry;

public interface EntryObserver {
    void onAdded(DiaryEntry entry);
    void onRemoved(DiaryEntry entry);
}