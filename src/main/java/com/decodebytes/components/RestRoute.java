package com.decodebytes.components;

import com.decodebytes.beans.NameAddress;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    restConfiguration()
        .component("jetty")
        .host("0.0.0.0")
        .port(8080)
        .bindingMode(RestBindingMode.json)
        .enableCORS(true);

    rest("/api")
        .produces("application/json")
        .post("/nameAddress")
        .type(NameAddress.class)
        .routeId("nameAddressPost")
        .to("direct:nameAddress");

    from("direct:nameAddress")
        .log(LoggingLevel.INFO, "Processing nameAddress: ${body}")
        //        .process(new InboundMessageProcessor())
        //        .convertBodyTo(String.class)
        //        .to("file:src/csv/output?fileName=output.csv&fileExist=append&appendChars=\\n");
        .to("jpa:" + NameAddress.class.getName());
  }
}
