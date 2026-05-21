package studentsdiary_lab2.factory;

import java.time.LocalDateTime;

import studentsdiary_lab2.model.Address;
import studentsdiary_lab2.model.EntryGroup;
import studentsdiary_lab2.model.EventEntry;
import studentsdiary_lab2.model.NoteEntry;

public interface EntryFactory {
    NoteEntry createNote(String title, String text);
    EventEntry createEvent(String title, LocalDateTime eventAt, LocalDateTime eventEndAt, Address location);
    EntryGroup createGroup(String title);
}