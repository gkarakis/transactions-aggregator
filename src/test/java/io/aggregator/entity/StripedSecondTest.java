package io.aggregator.entity;

import com.google.protobuf.Empty;
import io.aggregator.TimeTo;
import io.aggregator.api.StripedSecondApi;
import kalix.javasdk.testkit.EventSourcedResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StripedSecondTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    StripedSecondTestKit service = StripedSecondTestKit.of(StripedSecond::new);
    // // use the testkit to execute a command
    // // of events emitted, or a final updated state:
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // EventSourcedResult<SomeResponse> result = service.someOperation(command);
    // // verify the emitted events
    // ExpectedEvent actualEvent = result.getNextEventOfType(ExpectedEvent.class);
    // assertEquals(expectedEvent, actualEvent);
    // // verify the final state after applying the events
    // assertEquals(expectedState, service.getState());
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  public void addLedgerItemsTest() {
    StripedSecondTestKit service = StripedSecondTestKit.of(StripedSecond::new);
    TimeTo.From now = TimeTo.fromNow();

    StripedSecondApi.AddLedgerItemsCommand command1 = StripedSecondApi.AddLedgerItemsCommand.newBuilder()
        .setMerchantId("tesco")
        .setShopId("chelsea")
        .setStripe(1)
        .setEpochSecond(now.toEpochSecond())
        .setTimestamp(now.toTimestamp())
        .setTransactionId("txn-1")
        .addLedgerItem(StripedSecondApi.LedgerItem.newBuilder()
            .setAccountFrom("AF")
            .setAccountTo("AT")
            .setServiceCode("SVC1")
            .setAmount("1.11")
            .build())
        .build();
    StripedSecondApi.AddLedgerItemsCommand command2 = StripedSecondApi.AddLedgerItemsCommand.newBuilder()
        .setMerchantId("tesco")
        .setShopId("chelsea")
        .setStripe(1)
        .setEpochSecond(now.toEpochSecond())
        .setTimestamp(now.toTimestamp())
        .setTransactionId("txn-1")
        .addLedgerItem(StripedSecondApi.LedgerItem.newBuilder()
            .setAccountFrom("AF2")
            .setAccountTo("AT2")
            .setServiceCode("SVC1")
            .setAmount("1.22")
            .build())
        .build();

    EventSourcedResult<Empty> result = service.addLedgerItems(command1);
    StripedSecondEntity.StripedSecondState state = service.getState();
    assertEquals("tesco", state.getMerchantKey().getMerchantId());
    assertEquals("chelsea", state.getShopId());
    assertEquals(1, state.getStripe());
    assertEquals(now.toEpochSecond(), state.getEpochSecond());
    assertEquals(1, state.getLedgeringActivityCount());
    assertEquals("txn-1", state.getLedgeringActivity(0).getLedgeringActivityKey().getTransactionId());
    assertEquals("AF", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountFrom());
    assertEquals("AT", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountTo());
    assertEquals("SVC1", state.getLedgeringActivity(0).getLedgeringActivityKey().getServiceCode());
    assertEquals("1.11", state.getLedgeringActivity(0).getAmount());

    service.addLedgerItems(command1);
    state = service.getState();
    assertEquals("tesco", state.getMerchantKey().getMerchantId());
    assertEquals("chelsea", state.getShopId());
    assertEquals(1, state.getStripe());
    assertEquals(now.toEpochSecond(), state.getEpochSecond());
    assertEquals(1, state.getLedgeringActivityCount());
    assertEquals("txn-1", state.getLedgeringActivity(0).getLedgeringActivityKey().getTransactionId());
    assertEquals("AF", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountFrom());
    assertEquals("AT", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountTo());
    assertEquals("SVC1", state.getLedgeringActivity(0).getLedgeringActivityKey().getServiceCode());
    assertEquals("1.11", state.getLedgeringActivity(0).getAmount());

    service.addLedgerItems(command2);
    state = service.getState();
    assertEquals("tesco", state.getMerchantKey().getMerchantId());
    assertEquals("chelsea", state.getShopId());
    assertEquals(1, state.getStripe());
    assertEquals(now.toEpochSecond(), state.getEpochSecond());
    assertEquals(2, state.getLedgeringActivityCount());
    assertEquals("txn-1", state.getLedgeringActivity(0).getLedgeringActivityKey().getTransactionId());
    assertEquals("AF", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountFrom());
    assertEquals("AT", state.getLedgeringActivity(0).getLedgeringActivityKey().getAccountTo());
    assertEquals("SVC1", state.getLedgeringActivity(0).getLedgeringActivityKey().getServiceCode());
    assertEquals("1.11", state.getLedgeringActivity(0).getAmount());
    assertEquals("txn-1", state.getLedgeringActivity(1).getLedgeringActivityKey().getTransactionId());
    assertEquals("AF2", state.getLedgeringActivity(1).getLedgeringActivityKey().getAccountFrom());
    assertEquals("AT2", state.getLedgeringActivity(1).getLedgeringActivityKey().getAccountTo());
    assertEquals("SVC1", state.getLedgeringActivity(1).getLedgeringActivityKey().getServiceCode());
    assertEquals("1.22", state.getLedgeringActivity(1).getAmount());
  }

  @Test
  @Disabled("to be implemented")
  public void aggregateStripedSecondTest() {
    StripedSecondTestKit service = StripedSecondTestKit.of(StripedSecond::new);
    // AggregateStripedSecondCommand command = AggregateStripedSecondCommand.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.aggregateStripedSecond(command);
  }

}
