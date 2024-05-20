package com.decodebytes.components;

import java.util.logging.Logger;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LegacyFileRoute extends RouteBuilder {

  Logger logger = Logger.getLogger(LegacyFileRoute.class.getName());

  @Override
  public void configure() throws Exception {
    from("file:src/data/input?noop=true")
        .routeId("LegacyFileRoute")
        .process(
            exchange -> {
              String input = exchange.getIn().getBody(String.class);
              logger.info("Processing file: " + input);
            })
        .to("file:src/data/output?fileName=outputFile.txt")
        .log(LoggingLevel.INFO, "LegacyFileRoute", "Moved file");
    ;
  }
}
