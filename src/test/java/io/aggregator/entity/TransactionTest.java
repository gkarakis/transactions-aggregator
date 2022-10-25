package io.aggregator.entity;

import io.aggregator.TimeTo;
import io.aggregator.api.TransactionApi;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class TransactionTest {

  @Ignore
  @Test
  public void exampleTest() {
    // TransactionTestKit testKit = TransactionTestKit.of(Transaction::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // EventSourcedResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the emitted events
    // ExpectedEvent actualEvent = result.getNextEventOfType(ExpectedEvent.class);
    // assertEquals(expectedEvent, actualEvent)
    // verify the final state after applying the events
    // assertEquals(expectedState, testKit.getState());
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void paymentPricedTest() {
    TransactionTestKit testKit = TransactionTestKit.of(Transaction::new);

    var now = TimeTo.now();
    var response = testKit.paymentPriced(
        TransactionApi.PaymentPricedCommand
            .newBuilder()
            .setTransactionId("transaction-1")
            .setShopId("shop-1")
            .setEventType("event-type-1")
                .addPricedItem(TransactionApi.PricedItem.newBuilder()
                        .setServiceCode("SVC1")
                        .setPricedItemAmount("123.45")
                        .build())
            .setTimestamp(now)
            .build());

    var incidentAdded = response.getNextEventOfType(TransactionEntity.IncidentAdded.class);
    assertEquals("transaction-1", incidentAdded.getTransactionId());
    assertEquals("shop-1", incidentAdded.getShopId());
    assertEquals(now, incidentAdded.getIncidentTimestamp());
    assertEquals("event-type-1", incidentAdded.getTransactionIncident().getEventType());
    assertEquals(1, incidentAdded.getTransactionIncident().getTransactionIncidentServiceCount());
    assertEquals("SVC1", incidentAdded.getTransactionIncident().getTransactionIncidentService(0).getServiceCode());
    assertEquals("123.45", incidentAdded.getTransactionIncident().getTransactionIncidentService(0).getServiceAmount());

    var state = testKit.getState();
    assertEquals("transaction-1", state.getTransactionId());
    assertEquals("shop", state.getMerchantId());
    assertEquals("shop-1", state.getShopId());
    assertEquals(1, state.getTransactionIncidentCount());
    assertEquals(1, state.getTransactionIncident(0).getTransactionIncidentServiceCount());
    assertEquals("SVC1", state.getTransactionIncident(0).getTransactionIncidentService(0).getServiceCode());
    assertEquals("123.45", state.getTransactionIncident(0).getTransactionIncidentService(0).getServiceAmount());
  }
}
