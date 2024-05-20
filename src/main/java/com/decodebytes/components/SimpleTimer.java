package com.decodebytes.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleTimer extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("timer:simpletimer?period=10000")
        .routeId("SimpleTimer")
        //        .setHeader("firedTime")
        //        .simple("${date:now:yyyy-MM-dd HH:mm:ss}")
        .setBody(constant("Hello World"))
        //        .simple("Hello World Camel fired at ${header.firedTime}")
        .log(LoggingLevel.INFO, "SimpleTimer", "${body}");
  }
}
