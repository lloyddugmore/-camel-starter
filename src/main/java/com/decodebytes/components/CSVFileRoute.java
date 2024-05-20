package com.decodebytes.components;

import com.decodebytes.processor.InboundMessageProcessor;
import java.util.logging.Logger;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.springframework.stereotype.Component;

@Component
public class CSVFileRoute extends RouteBuilder {

  Logger logger = Logger.getLogger(CSVFileRoute.class.getName());
  BeanIODataFormat inboundDataFormat =
      new BeanIODataFormat("InboundMessageBeanIOMapping.xml", "inputMessageStream");

  @Override
  public void configure() throws Exception {
    from("file:src/csv/input?noop=true")
        .routeId("CSVFileRoute")
        .setHeader("CamelFileName", simple("${file:name.noext}.csv"))
        .log("Processing file: ${header.CamelFileName}")
        .split(body().tokenize("\n", 1, true))
        .unmarshal(inboundDataFormat)
        .process(new InboundMessageProcessor())
        .convertBodyTo(String.class)
        .to("file:src/csv/output?fileName=output.csv&fileExist=append&appendChars=\\n")
        .log("Moved file: ${header.CamelFileName}")
        .end();
  }
}
