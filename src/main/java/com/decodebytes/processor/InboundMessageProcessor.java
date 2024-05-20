package com.decodebytes.processor;

import com.decodebytes.beans.NameAddress;
import com.decodebytes.beans.OutboundNameAddress;
import java.util.logging.Logger;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InboundMessageProcessor implements Processor {

  Logger logger = Logger.getLogger(InboundMessageProcessor.class.getName());

  @Override
  public void process(Exchange exchange) throws Exception {
    NameAddress fileData = exchange.getIn().getBody(NameAddress.class);
    logger.info("Processing line: " + fileData.toString());

    OutboundNameAddress outboundNameAddress =
        new OutboundNameAddress(
            fileData.getName(),
            fileData.getHouseNumber()
                + " "
                + fileData.getCity()
                + " "
                + fileData.getProvince()
                + " "
                + fileData.getPostalCode());

    exchange.getIn().setBody(outboundNameAddress);
  }
}
