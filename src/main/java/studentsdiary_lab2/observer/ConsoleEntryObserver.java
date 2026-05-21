package studentsdiary_lab2.observer;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;

@Component
@Scope("singleton")
@Profile({"dev", "default"})
public class ConsoleEntryObserver implements EntryObserver {

    @Override
    public void onAdded(DiaryEntry entry) {
        System.out.println("Добавлено: " + entry.getTitle());
    }

    @Override
    public void onRemoved(DiaryEntry entry) {
        System.out.println("Удалено: " + entry.getTitle());
    }
}