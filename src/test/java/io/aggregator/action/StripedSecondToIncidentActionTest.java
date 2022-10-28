package io.aggregator.action;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StripedSecondToIncidentActionTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    StripedSecondToIncidentActionTestKit service = StripedSecondToIncidentActionTestKit.of(StripedSecondToIncidentAction::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Disabled("to be implemented")
  public void onStripedSecondAggregatedTest() {
    StripedSecondToIncidentActionTestKit testKit = StripedSecondToIncidentActionTestKit.of(StripedSecondToIncidentAction::new);
    // ActionResult<Empty> result = testKit.onStripedSecondAggregated(StripedSecondEntity.StripedSecondAggregated.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void ignoreOtherEventsTest() {
    StripedSecondToIncidentActionTestKit testKit = StripedSecondToIncidentActionTestKit.of(StripedSecondToIncidentAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
