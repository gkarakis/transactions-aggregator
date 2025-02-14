package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class MinuteToHourActionTest {

  @Test
  public void exampleTest() {
    MinuteToHourActionTestKit testKit = MinuteToHourActionTestKit.of(MinuteToHourAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onMinuteCreatedTest() {
    MinuteToHourActionTestKit testKit = MinuteToHourActionTestKit.of(MinuteToHourAction::new);
    // ActionResult<Empty> result = testKit.onMinuteCreated(MinuteEntity.MinuteCreated.newBuilder()...build());
  }

  @Test
  public void onMinuteAggregatedTest() {
    MinuteToHourActionTestKit testKit = MinuteToHourActionTestKit.of(MinuteToHourAction::new);
    // ActionResult<Empty> result = testKit.onMinuteAggregated(MinuteEntity.MinuteAggregated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    MinuteToHourActionTestKit testKit = MinuteToHourActionTestKit.of(MinuteToHourAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
