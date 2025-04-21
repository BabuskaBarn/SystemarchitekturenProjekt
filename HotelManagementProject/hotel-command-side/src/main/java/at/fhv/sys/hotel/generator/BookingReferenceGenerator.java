package at.fhv.sys.hotel.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingReferenceGenerator {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final int MAX_VALUE = 999;

    public static String generate() {
        int current = counter.getAndUpdate(i -> (i + 1) % (MAX_VALUE + 1));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        String paddedCounter = String.format("%03d", current);
        return timestamp + "-" + paddedCounter;
    }

    // returnt einen String der so aussieht 2504211742-007 damit wäre es immer eindeutig
}
