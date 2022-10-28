package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DayToPaymentActionTest {

  @Test
  public void exampleTest() {
    DayToPaymentActionTestKit testKit = DayToPaymentActionTestKit.of(DayToPaymentAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onDayAggregatedTest() {
    DayToPaymentActionTestKit testKit = DayToPaymentActionTestKit.of(DayToPaymentAction::new);
    // ActionResult<Empty> result = testKit.onDayAggregated(DayEntity.DayAggregated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    DayToPaymentActionTestKit testKit = DayToPaymentActionTestKit.of(DayToPaymentAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
