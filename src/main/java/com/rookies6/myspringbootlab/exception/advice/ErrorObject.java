package com.rookies6.myspringbootlab.exception.advice;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class ErrorObject {
    //에러 코드
    private Integer statusCode;
    //에러 메세지
    private String message;
    //에러 발생 시간
    private String timestamp;

    public String getTimestamp() {
        LocalDateTime ldt = LocalDateTime.now();
        return DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss E a", 
                Locale.KOREA).format(ldt);
    }
}