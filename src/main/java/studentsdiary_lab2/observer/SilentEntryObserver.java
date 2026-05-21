package studentsdiary_lab2.observer;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.DiaryEntry;

@Component
@Scope("singleton")
@Profile("prod")
public class SilentEntryObserver implements EntryObserver {

    @Override
    public void onAdded(DiaryEntry entry) {
    }

    @Override
    public void onRemoved(DiaryEntry entry) {
    }
}