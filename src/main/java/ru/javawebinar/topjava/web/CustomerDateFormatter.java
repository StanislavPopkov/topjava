package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class CustomerDateFormatter implements Formatter<LocalDate> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        log.debug("parse date:", s);
        if(s == null && s.isEmpty()){
            LocalDate localDate = parseStringToLocalDate("1970-01-01");
            return localDate;
        }
        return parseStringToLocalDate(s);

    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        log.debug("print date:", localDate.toString());
        return "LocalDate: "+ localDate.toString();
    }

    public LocalDate parseStringToLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }
}
