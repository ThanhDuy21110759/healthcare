package com.clinic.pharmacy.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class DataConvention {

    public static final Integer DEFAULT_INTEGER = 0;
    public static final String DEFAULT_STRING = "";
    public static final BigDecimal DEFAULT_BIGDECIMAL = BigDecimal.ZERO;
    public static final LocalDate DEFAULT_LOCALDATE = LocalDate.of(1970, 1, 1);

}
