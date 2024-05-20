package com.decodebytes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
public class LegacyFileRouteTest {

  @Autowired private CamelContext camelContext;
  @Autowired private ProducerTemplate producerTemplate;

  @EndpointInject("mock:legacyFileMockedEndpoint")
  private MockEndpoint mockEndpoint;

  // below test breaks, as it depends on the src/data/input/inputFile.txt file being in the right
  // location at time of running the tests, which is not guaranteed.
  //  @Test
  //  public void testLegacyFileRoute() throws Exception {
  //    String expectedBody =
  //        "this is an input file that will be processed and moved to the output directory.";
  //    mockEndpoint.expectedBodiesReceived(expectedBody);
  //    mockEndpoint.expectedMessageCount(1);
  //
  //    AdviceWith.adviceWith(
  //        camelContext,
  //        "LegacyFileRoute",
  //        routeBuilder -> {
  //          routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
  //        });
  //
  //    camelContext.start();
  //
  //    mockEndpoint.assertIsSatisfied();
  //  }

  // below test is a better way to test the file move route, as it mocks the from endpoint
  @Test
  public void testFileMoveByMockingFromEndpoint() throws Exception {
    String expectedBody = "this is an input data after mocking the from endpoint.";
    mockEndpoint.expectedBodiesReceived(expectedBody);
    mockEndpoint.expectedMessageCount(1);

    AdviceWith.adviceWith(
        camelContext,
        "LegacyFileRoute",
        routeBuilder -> {
          routeBuilder.replaceFromWith("direct:mockstart");
          routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
        });

    camelContext.start();
    producerTemplate.sendBody("direct:mockstart", expectedBody);

    mockEndpoint.assertIsSatisfied();
  }
}
