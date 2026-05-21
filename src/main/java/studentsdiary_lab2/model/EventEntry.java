package studentsdiary_lab2.model;

import java.time.LocalDateTime;

public final class EventEntry extends DiaryEntry {

    private LocalDateTime eventAt;
    private LocalDateTime eventEndAt;
    private Address address;

    public EventEntry(String title, LocalDateTime createdAt,
                      LocalDateTime eventAt, LocalDateTime eventEndAt,
                      Address address) {
        super(title, createdAt);
        setEventAt(eventAt);
        setEventEndAt(eventEndAt);
        setAddress(address);
    }

    public LocalDateTime getEventAt() {
        return eventAt;
    }

    public void setEventAt(LocalDateTime eventAt) {
        if (eventAt == null) {
            throw new IllegalArgumentException("Дата начала события не задана");
        }
        this.eventAt = eventAt;
        if (eventEndAt != null && !eventEndAt.isAfter(eventAt)) {
            throw new IllegalArgumentException("Окончание должно быть позже начала");
        }
    }

    public LocalDateTime getEventEndAt() {
        return eventEndAt;
    }

    public void setEventEndAt(LocalDateTime eventEndAt) {
        if (eventEndAt == null) {
            throw new IllegalArgumentException("Дата окончания события не задана");
        }
        if (!eventEndAt.isAfter(eventAt)) {
            throw new IllegalArgumentException("Окончание должно быть позже начала");
        }
        this.eventEndAt = eventEndAt;
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