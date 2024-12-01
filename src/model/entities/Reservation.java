package model.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.exceptions.DomainException;

public class Reservation {
	private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private Integer roomNumber;
	private LocalDate checkin;
	private LocalDate checkout;
	
	public Reservation(Integer roomNumber, LocalDate checkin, LocalDate checkout) throws DomainException {
		if (!checkout.isAfter(checkin)) {
			throw new DomainException("Check-out date must be after check-in date");
		}
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
	
	public void updateDates(LocalDate checkin, LocalDate checkout) throws DomainException{
		LocalDate now = LocalDate.now();
		
		if (checkin.isBefore(now) || checkout.isBefore(now)) {
			throw new DomainException("Reservation dates for update must be future dates");
		}
		
		if (!checkout.isAfter(checkin)) {
			throw new DomainException("Check-out date must be after check-in date");
		}
		
		this.checkin = checkin;
		this.checkout = checkout;
	}

	@Override
	public String toString() {
		return String.format(
				"Room %d, check-in: %s, check-out: %s, %d nights",
				roomNumber, checkin.format(FORMATTER), checkout.format(FORMATTER), 
				duration());
	}
}
