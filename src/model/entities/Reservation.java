package model.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
	private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private Integer roomNumber;
	private LocalDate checkin;
	private LocalDate checkout;
	
	public Reservation(Integer roomNumber, LocalDate checkin, LocalDate checkout) {
		this.roomNumber = roomNumber;
		this.checkin = checkin;
		this.checkout = checkout;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public LocalDate getCheckin() {
		return checkin;
	}

	public LocalDate getCheckout() {
		return checkout;
	}
	
	public long duration() {
		// long diff = ChronoUnit.DAYS.between(checkin, checkout);
		Duration duration = Duration.between(checkin.atStartOfDay(), checkout.atStartOfDay());
		return duration.toDays();
	}
	
	public String updateDates(LocalDate checkin, LocalDate checkout) {
		LocalDate now = LocalDate.now();
		
		if (checkin.isBefore(now) || checkout.isBefore(now)) {
			return "Reservation dates for update must be future dates";
		}
		
		if (!checkout.isAfter(checkin)) {
			return "Check-out date must be after check-in date";
		}
		
		this.checkin = checkin;
		this.checkout = checkout;
		return null;
	}

	@Override
	public String toString() {
		return String.format(
				"Room %d, check-in: %s, check-out: %s, %d nights",
				roomNumber, checkin.format(FORMATTER), checkout.format(FORMATTER), 
				duration());
	}
}
