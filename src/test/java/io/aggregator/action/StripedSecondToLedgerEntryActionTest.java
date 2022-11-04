package io.aggregator.action;

import akka.stream.javadsl.Source;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.aggregator.action.StripedSecondToLedgerEntryAction;
import io.aggregator.action.StripedSecondToLedgerEntryActionTestKit;
import io.aggregator.entity.StripedSecondEntity;
import kalix.javasdk.testkit.ActionResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StripedSecondToLedgerEntryActionTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    StripedSecondToLedgerEntryActionTestKit service = StripedSecondToLedgerEntryActionTestKit.of(StripedSecondToLedgerEntryAction::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Disabled("to be implemented")
  public void onStripedSecondLedgeringActivitiesAddedTest() {
    StripedSecondToLedgerEntryActionTestKit testKit = StripedSecondToLedgerEntryActionTestKit.of(StripedSecondToLedgerEntryAction::new);
    // ActionResult<Empty> result = testKit.onStripedSecondLedgeringActivitiesAdded(StripedSecondEntity.StripedSecondLedgeringActivitiesAdded.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void onStripedSecondAggregatedTest() {
    StripedSecondToLedgerEntryActionTestKit testKit = StripedSecondToLedgerEntryActionTestKit.of(StripedSecondToLedgerEntryAction::new);
    // ActionResult<Empty> result = testKit.onStripedSecondAggregated(StripedSecondEntity.StripedSecondAggregated.newBuilder()...build());
  }

  @Test
  @Disabled("to be implemented")
  public void ignoreOtherEventsTest() {
    StripedSecondToLedgerEntryActionTestKit testKit = StripedSecondToLedgerEntryActionTestKit.of(StripedSecondToLedgerEntryAction::new);
    // ActionResult<Empty> result = testKit.ignoreOtherEvents(Any.newBuilder()...build());
  }

}
