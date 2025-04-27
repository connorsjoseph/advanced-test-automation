package core.performance;

import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;

import io.micrometer.core.instrument.MeterRegistry;

public class PerformanceMonitor {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitor.class);
    private static final Map<String, Timer> pageLoadTimers = new ConcurrentHashMap<>();
    private static final MeterRegistry registry = io.micrometer.core.instrument.Metrics.globalRegistry;
    private static final Map<String, Long> startTimes = new ConcurrentHashMap<>();

    public static void startPageLoadTimer(String pageName) {
        Objects.requireNonNull(pageName, "Page name cannot be null");
        if (pageName.trim().isEmpty()) {
            throw new IllegalArgumentException("Page name cannot be empty");
        }

        startTimes.put(pageName, System.currentTimeMillis());
        logger.info("Starting page load timer for: {}", pageName);
    }

    public static void stopPageLoadTimer(String pageName) {
        Objects.requireNonNull(pageName, "Page name cannot be null");

        Long startTime = startTimes.remove(pageName); // Remove entry to prevent memory leak
        if (startTime == null) {
            logger.warn("No start time found for page: {}", pageName);
            return;
        }

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Page {} loaded in {} ms", pageName, duration);

        // Get or create timer
        Timer timer = pageLoadTimers.computeIfAbsent(pageName, key ->
                Timer.builder("page.load.time")
                        .tag("page", key)
                        .register(registry));

        timer.record(java.time.Duration.ofMillis(duration));
    }

    public static Map<String, Double> getAverageLoadTimes() {
        Map<String, Double> averages = new ConcurrentHashMap<>();
        pageLoadTimers.forEach((page, timer) -> {
            averages.put(page, timer.mean(java.util.concurrent.TimeUnit.MILLISECONDS));
        });
        return averages;
    }

    // Method to clean up timers if needed
    public static void removeTimer(String pageName) {
        pageLoadTimers.remove(pageName);
        startTimes.remove(pageName);
    }
}