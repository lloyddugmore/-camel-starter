package com.decodebytes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
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
public class SimpleTimerRouteTest {

  @Autowired private CamelContext camelContext;

  @EndpointInject("mock:simpleTimerMockedEndpoint")
  private MockEndpoint mockEndpoint;

  @Test
  public void testSimpleTimer() throws Exception {
    String expectedBody = "Hello World";
    mockEndpoint.expectedBodiesReceived(expectedBody);
    mockEndpoint.expectedMinimumMessageCount(1);

    AdviceWith.adviceWith(
        camelContext,
        "SimpleTimer",
        routeBuilder -> {
          routeBuilder.weaveAddLast().to(mockEndpoint);
        });

    camelContext.start();

    mockEndpoint.assertIsSatisfied();
  }
}
