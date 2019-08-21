package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class CustomerTimeFormatter implements Formatter<LocalTime> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        log.debug("parse time:", s);
        if(s == null && s.isEmpty()){
            LocalTime localTime = parseStringToLocalTime("00:00:00");
            return localTime;
        }
        return parseStringToLocalTime(s);
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        log.debug("print time:", localTime.toString());
        return "LocalTime: "+ localTime.toString();
    }

    public static LocalTime parseStringToLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
