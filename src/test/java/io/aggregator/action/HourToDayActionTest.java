package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class HourToDayActionTest {

  @Test
  public void exampleTest() {
    HourToDayActionTestKit testKit = HourToDayActionTestKit.of(HourToDayAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onHourCreatedTest() {
    HourToDayActionTestKit testKit = HourToDayActionTestKit.of(HourToDayAction::new);
    // ActionResult<Empty> result = testKit.onHourCreated(HourEntity.HourCreated.newBuilder()...build());
  }

  @Test
  public void onHourAggregatedTest() {
    HourToDayActionTestKit testKit = HourToDayActionTestKit.of(HourToDayAction::new);
    // ActionResult<Empty> result = testKit.onHourAggregated(HourEntity.HourAggregated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    HourToDayActionTestKit testKit = HourToDayActionTestKit.of(HourToDayAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
