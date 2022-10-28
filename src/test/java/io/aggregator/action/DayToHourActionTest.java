package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class DayToHourActionTest {

  @Test
  public void exampleTest() {
    DayToHourActionTestKit testKit = DayToHourActionTestKit.of(DayToHourAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onDayAggregationRequestedTest() {
    DayToHourActionTestKit testKit = DayToHourActionTestKit.of(DayToHourAction::new);
    // ActionResult<Empty> result = testKit.onDayAggregationRequested(DayEntity.DayAggregationRequested.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    DayToHourActionTestKit testKit = DayToHourActionTestKit.of(DayToHourAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
