package com.everoad.blog.backend.core.lib;

import org.springframework.http.MediaType;

import java.time.format.DateTimeFormatter;

public class Const {

  public final static String ACTUATOR = "ACTUATOR";

  public final static String REQUEST_BODY = "REQUEST_BODY";

  public final static String UTF_8 = ";charset=utf-8";
  public final static String APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON_VALUE + UTF_8;
  public final static String APPLICATION_OCTET_STREAM_VALUE = MediaType.APPLICATION_OCTET_STREAM_VALUE + UTF_8;

  public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
