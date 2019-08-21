package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class DateAndTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<DateAndTimeFormat> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> setTypes = new HashSet<Class<?>>();
        setTypes.add(String.class);
        setTypes.add(LocalDate.class);
        setTypes.add(LocalTime.class);
        return setTypes;
    }

    @Override
    public Printer<?> getPrinter(DateAndTimeFormat dateAndTimeFormat, Class<?> aClass) {
        log.debug("DateAndTimeAnnotationFormatterFactory getPrinter:");
        if(dateAndTimeFormat.iso() == DateAndTimeFormat.ISO.DATE){
            return new CustomerDateFormatter();
        }
        else return new CustomerTimeFormatter();
    }

    @Override
    public Parser<?> getParser(DateAndTimeFormat dateAndTimeFormat, Class<?> aClass) {
        log.debug("DateAndTimeAnnotationFormatterFactory getParser:");
        if(dateAndTimeFormat.iso() == DateAndTimeFormat.ISO.DATE){
            return new CustomerDateFormatter();
        }
        else return new CustomerTimeFormatter();
    }


}
