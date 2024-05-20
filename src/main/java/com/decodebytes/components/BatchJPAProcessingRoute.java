package com.decodebytes.components;

import com.decodebytes.beans.NameAddress;
import com.decodebytes.processor.InboundMessageProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BatchJPAProcessingRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("timer:readDB?period=12000")
        .routeId("readDB")
        .to("jpa:" + NameAddress.class.getName() + "?namedQuery=NameAddress.findAll")
        .split(body())
        .process(new InboundMessageProcessor())
        .convertBodyTo(String.class)
        .to("file:src/csv/output?fileName=output.csv&fileExist=append&appendChars=\\n")
        .end();
  }
}
