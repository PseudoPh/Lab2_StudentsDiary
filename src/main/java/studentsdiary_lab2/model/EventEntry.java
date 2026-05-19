package studentsdiary_lab2.model;

import java.time.LocalDateTime;

public final class EventEntry extends DiaryEntry {

    private LocalDateTime eventAt;
    private Address address;

    public EventEntry(String title, LocalDateTime createdAt,
                      LocalDateTime eventAt, Address address) {
        super(title, createdAt);
        setEventAt(eventAt);
        setAddress(address);
    }

    public LocalDateTime getEventAt() {
        return eventAt;
    }

    public void setEventAt(LocalDateTime eventAt) {
        if (eventAt == null) {
            throw new IllegalArgumentException("Дата события не задана");
        }
        this.eventAt = eventAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Адрес не задан");
        }
        this.address = address;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
}