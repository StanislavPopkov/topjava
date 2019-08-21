package ru.javawebinar.topjava.web;

import java.lang.annotation.*;

@Target(value={ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateAndTimeFormat {

    DateAndTimeFormat.ISO iso() default DateAndTimeFormat.ISO.NONE;

    String pattern() default "";

    public static enum ISO {
        DATE,
        TIME,
        NONE;

        private ISO() {
        }
    }


}
