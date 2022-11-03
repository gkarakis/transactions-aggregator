package io.aggregator.entity;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import io.aggregator.api.LedgerEntryApi;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LedgerEntryTest {

  @Test
  @Disabled("to be implemented")
  public void exampleTest() {
    LedgerEntryTestKit service = LedgerEntryTestKit.of(LedgerEntry::new);
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
  @Disabled("to be implemented")
  public void createLedgerEntryTest() {
    LedgerEntryTestKit service = LedgerEntryTestKit.of(LedgerEntry::new);
    // CreateLedgerEntryCommand command = CreateLedgerEntryCommand.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.createLedgerEntry(command);
  }


  @Test
  @Disabled("to be implemented")
  public void addPaymentTest() {
    LedgerEntryTestKit service = LedgerEntryTestKit.of(LedgerEntry::new);
    // AddPaymentCommand command = AddPaymentCommand.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.addPayment(command);
  }


  @Test
  @Disabled("to be implemented")
  public void getLedgerEntryTest() {
    LedgerEntryTestKit service = LedgerEntryTestKit.of(LedgerEntry::new);
    // GetLedgerEntryRequest command = GetLedgerEntryRequest.newBuilder()...build();
    // EventSourcedResult<GetLedgerEntryResponse> result = service.getLedgerEntry(command);
  }

  @org.junit.jupiter.api.Test
  public void happyPath() {

    String incidentAmount = "100";
    String paymentId = "1234";
    String merchantId = "merchant1";
    String shopId = "merchant1Shop1";

    Timestamp t = Timestamps.fromDate(new Date());

    LedgerEntryTestKit service = LedgerEntryTestKit.of(LedgerEntry::new);
    TransactionMerchantKey.TransactionKey key = TransactionMerchantKey.TransactionKey.newBuilder()
        .setTransactionId("transId1")
        .setServiceCode("srv1")
        .setAccountFrom("accFrom")
        .setAccountTo("accTo")
        .build();
    var createCommand = LedgerEntryApi.CreateLedgerEntryCommand.newBuilder()
        .setTransactionId(key.getTransactionId())
        .setServiceCode(key.getServiceCode())
        .setAccountFrom(key.getAccountFrom())
        .setAccountTo(key.getAccountTo())
        .setAmount(incidentAmount)
        .setMerchantId(merchantId)
        .setShopId(shopId)
        .setTimestamp(t)
        .build();
    var createResponse = service.createLedgerEntry(createCommand);
    var incidentCreated = createResponse.getNextEventOfType(LedgerEntryEntity.LedgerEntryCreated.class);
    assertEquals(key.getTransactionId(), incidentCreated.getLedgerEntryKey().getTransactionId());
    assertEquals(key.getServiceCode(), incidentCreated.getLedgerEntryKey().getServiceCode());
    assertEquals(key.getAccountFrom(), incidentCreated.getLedgerEntryKey().getAccountFrom());
    assertEquals(key.getAccountTo(), incidentCreated.getLedgerEntryKey().getAccountTo());
    assertEquals(incidentAmount,incidentCreated.getAmount());
    assertEquals(merchantId,incidentCreated.getMerchantId());
    assertEquals(shopId,incidentCreated.getShopId());

    var addPaymentCommand = LedgerEntryApi.AddPaymentCommand.newBuilder()
        .setTransactionId(key.getTransactionId())
        .setServiceCode(key.getServiceCode())
        .setAccountFrom(key.getAccountFrom())
        .setAccountTo(key.getAccountTo())
        .setPaymentId(paymentId)
        .setTimestamp(t).build();
    var addPaymentResponse = service.addPayment(addPaymentCommand);
    assertFalse(addPaymentResponse.isError());
    var incidentPaymentAdded = addPaymentResponse.getNextEventOfType(LedgerEntryEntity.LedgerEntryPaymentAdded.class);

    assertEquals(paymentId,incidentPaymentAdded.getPaymentId());
  }
}
