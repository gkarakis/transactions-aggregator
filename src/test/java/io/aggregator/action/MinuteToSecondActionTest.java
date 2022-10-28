package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class MinuteToSecondActionTest {

  @Test
  public void exampleTest() {
    MinuteToSecondActionTestKit testKit = MinuteToSecondActionTestKit.of(MinuteToSecondAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onMinuteAggregationRequestedTest() {
    MinuteToSecondActionTestKit testKit = MinuteToSecondActionTestKit.of(MinuteToSecondAction::new);
    // ActionResult<Empty> result = testKit.onMinuteAggregationRequested(MinuteEntity.MinuteAggregationRequested.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    MinuteToSecondActionTestKit testKit = MinuteToSecondActionTestKit.of(MinuteToSecondAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
