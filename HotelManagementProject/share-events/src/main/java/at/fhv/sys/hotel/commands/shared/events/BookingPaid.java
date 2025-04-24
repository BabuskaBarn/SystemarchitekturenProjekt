package at.fhv.sys.hotel.commands.shared.events;

import at.fhv.sys.hotel.commands.shared.events.Enums.PaymentOptions;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingPaid {

    private final Long bookingId;
    private final PaymentOptions paymentOptions;
    private final double amount;

    @JsonCreator
    public BookingPaid(
            @JsonProperty("bookingId") Long bookingId,
            @JsonProperty("paymentMethod") PaymentOptions paymentOptions,
            @JsonProperty("amount") double amount
    ) {
        this.bookingId = bookingId;
        this.paymentOptions = paymentOptions;
        this.amount = amount;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public double getAmount() {
        return amount;
    }
}
