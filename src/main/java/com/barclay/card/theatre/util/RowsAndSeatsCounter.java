package com.barclay.card.theatre.util;

/**
 * @author Hari
 * A static class which has rowNameCounter and totalAvailableSeats..
 * As all the requests are only requestScope, rowCounter & totalAvailableSeats was initialized as ThreadLocal
 */
public class RowsAndSeatsCounter {

    private static ThreadLocal<Integer> rowNameCounter = ThreadLocal
	    .withInitial(() -> {
		return new Integer(1);
	    });

    private static ThreadLocal<Integer> totalAvailableSeatsInTheatre = ThreadLocal
	    .withInitial(() -> {
		return new Integer(0);
	    });

    public static int getRowCounter() {
	return rowNameCounter.get();
    }

    public static void setRowCounter(int newRowCounter) {
	rowNameCounter.set(newRowCounter);
    }

    public static int getTotalAvailableSeats() {
	return totalAvailableSeatsInTheatre.get();
    }

    public static void addToTotalAvailableSeats(int seats) {
	totalAvailableSeatsInTheatre.set(totalAvailableSeatsInTheatre.get()
		+ seats);
    }

    public static void deductTotalAvailableSeats(int seats) {
	totalAvailableSeatsInTheatre.set(totalAvailableSeatsInTheatre.get()
		- seats);
    }

    public static void setToDefaults() {
	rowNameCounter.set(1);
	totalAvailableSeatsInTheatre.set(0);
    }

    
}
