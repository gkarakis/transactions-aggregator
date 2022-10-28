package io.aggregator.action;

import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class SecondToMinuteActionTest {

  @Test
  public void exampleTest() {
    SecondToMinuteActionTestKit testKit = SecondToMinuteActionTestKit.of(SecondToMinuteAction::new);
    // use the testkit to execute a command
    // ActionResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void onSecondCreatedTest() {
    SecondToMinuteActionTestKit testKit = SecondToMinuteActionTestKit.of(SecondToMinuteAction::new);
    // ActionResult<Empty> result = testKit.onSecondCreated(SecondEntity.SecondCreated.newBuilder()...build());
  }

  @Test
  public void onSecondAggregatedTest() {
    SecondToMinuteActionTestKit testKit = SecondToMinuteActionTestKit.of(SecondToMinuteAction::new);
    // ActionResult<Empty> result = testKit.onSecondAggregated(SecondEntity.SecondAggregated.newBuilder()...build());
  }

  @Test
  public void ignoreOtherEventsTest() {
    SecondToMinuteActionTestKit testKit = SecondToMinuteActionTestKit.of(SecondToMinuteAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
