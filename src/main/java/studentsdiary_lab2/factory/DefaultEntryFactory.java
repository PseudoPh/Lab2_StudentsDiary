package studentsdiary_lab2.factory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import studentsdiary_lab2.model.Address;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;

@Component
public class DefaultEntryFactory implements EntryFactory {

    @Override
    public NoteEntry createNote(String title, String text) {
        return new NoteEntry(title, LocalDateTime.now(), text);
    }

    @Override
    public EventEntry createEvent(String title, LocalDateTime eventAt, LocalDateTime eventEndAt, Address location) {
        return new EventEntry(title, LocalDateTime.now(), eventAt, eventEndAt, location);
    }

    @Override
    public EntryGroup createGroup(String title) {
        return new EntryGroup(title, LocalDateTime.now());
    }
}