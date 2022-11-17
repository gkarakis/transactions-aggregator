package io.aggregator.entity;

import com.google.protobuf.Empty;
import io.aggregator.api.LedgerEntryApi;
import io.grpc.Status;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Event Sourced Entity Service described in your io/aggregator/api/ledger_entry_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LedgerEntry extends AbstractLedgerEntry {

  @SuppressWarnings("unused")
  private final String entityId;

  public LedgerEntry(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public LedgerEntryEntity.LedgerEntryState emptyState() {
    return LedgerEntryEntity.LedgerEntryState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> createLedgerEntry(LedgerEntryEntity.LedgerEntryState currentState, LedgerEntryApi.CreateLedgerEntryCommand createLedgerEntryCommand) {
    if (!currentState.equals(LedgerEntryEntity.LedgerEntryState.getDefaultInstance()))
      return effects().reply(Empty.getDefaultInstance());

    LedgerEntryEntity.LedgerEntryKey key = LedgerEntryEntity.LedgerEntryKey.newBuilder()
        .setTransactionId(createLedgerEntryCommand.getTransactionId())
        .setServiceCode(createLedgerEntryCommand.getServiceCode())
        .setAccountFrom(createLedgerEntryCommand.getAccountFrom())
        .setAccountTo(createLedgerEntryCommand.getAccountTo())
        .build();
    LedgerEntryEntity.LedgerEntryCreated event = LedgerEntryEntity.LedgerEntryCreated.newBuilder()
        .setLedgerEntryKey(key)
        .setMerchantId(createLedgerEntryCommand.getMerchantId())
        .setShopId(createLedgerEntryCommand.getShopId())
        .setIncidentTimestamp(createLedgerEntryCommand.getTimestamp())
        .setAmount(createLedgerEntryCommand.getAmount())
        .setEventType(createLedgerEntryCommand.getEventType())
        .build();

    return effects().emitEvent(event).thenReply(newState -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> addPayment(LedgerEntryEntity.LedgerEntryState currentState, LedgerEntryApi.AddPaymentCommand addPaymentCommand) {
    if (currentState.equals(LedgerEntryEntity.LedgerEntryState.getDefaultInstance()))
      return effects().error("LedgerEntry not created");

    if (!currentState.getPaymentId().isEmpty())
      return effects().reply(Empty.getDefaultInstance());

    LedgerEntryEntity.LedgerEntryPaymentAdded event = LedgerEntryEntity.LedgerEntryPaymentAdded.newBuilder()
        .setLedgerEntryKey(currentState.getLedgerEntryKey())
        .setPaymentTimestamp(addPaymentCommand.getTimestamp())
        .setPaymentId(addPaymentCommand.getPaymentId())
        .build();

    return effects().emitEvent(event).thenReply(newState -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<LedgerEntryApi.GetLedgerEntryResponse> getLedgerEntry(LedgerEntryEntity.LedgerEntryState currentState, LedgerEntryApi.GetLedgerEntryRequest getLedgerEntryRequest) {
    if (currentState.equals(LedgerEntryEntity.LedgerEntryState.getDefaultInstance()))
      return effects().error("Not found", Status.Code.NOT_FOUND);
    return effects().reply(LedgerEntryApi.GetLedgerEntryResponse.newBuilder()
        .setAmount(currentState.getAmount())
        .setPaymentId(currentState.getPaymentId())
        .build());
  }

  @Override
  public LedgerEntryEntity.LedgerEntryState ledgerEntryCreated(LedgerEntryEntity.LedgerEntryState currentState, LedgerEntryEntity.LedgerEntryCreated ledgerEntryCreated) {
    return LedgerEntryEntity.LedgerEntryState.newBuilder()
        .setLedgerEntryKey(ledgerEntryCreated.getLedgerEntryKey())
        .setAmount(ledgerEntryCreated.getAmount())
        .build();
  }

  @Override
  public LedgerEntryEntity.LedgerEntryState ledgerEntryPaymentAdded(LedgerEntryEntity.LedgerEntryState currentState, LedgerEntryEntity.LedgerEntryPaymentAdded ledgerEntryPaymentAdded) {
    return currentState.toBuilder().setPaymentId(ledgerEntryPaymentAdded.getPaymentId()).build();
  }

}
