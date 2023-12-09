package se.sevenfeet.musicapi.util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadIdConverter extends ClassicConverter {

    private static final AtomicInteger nextId = new AtomicInteger();

    private static final ConcurrentHashMap<String, String> name2Id = new ConcurrentHashMap<>();

    private static String getNextThreadId(String name) {
        return String.format("%04d", nextId.getAndIncrement());
    }

    @Override
    public String convert(ILoggingEvent event) {
        return name2Id.computeIfAbsent(event.getThreadName(), ThreadIdConverter::getNextThreadId);
    }
}
