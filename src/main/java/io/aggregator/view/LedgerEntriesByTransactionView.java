package io.aggregator.view;

import com.google.protobuf.Any;
import io.aggregator.entity.LedgerEntryEntity;
import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the View Service described in your io/aggregator/view/ledger_entries_by_transaction.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LedgerEntriesByTransactionView extends AbstractLedgerEntriesByTransactionView {

  public LedgerEntriesByTransactionView(ViewContext context) {}

  @Override
  public LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState emptyState() {
    return LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState> onLedgerEntryCreated(
    LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState state, LedgerEntryEntity.LedgerEntryCreated ledgerEntryCreated) {
    LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState newState = LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState.newBuilder()
        .setTransactionId(ledgerEntryCreated.getLedgerEntryKey().getTransactionId())
        .setServiceCode(ledgerEntryCreated.getLedgerEntryKey().getServiceCode())
        .setAccountFrom(ledgerEntryCreated.getLedgerEntryKey().getAccountFrom())
        .setAccountTo(ledgerEntryCreated.getLedgerEntryKey().getAccountTo())
        .setIncidentTimestamp(ledgerEntryCreated.getIncidentTimestamp())
        .setAmount(ledgerEntryCreated.getAmount())
        .setMerchantId(ledgerEntryCreated.getMerchantId())
        .setShopId(ledgerEntryCreated.getShopId())
        .setEventType(ledgerEntryCreated.getEventType())
        .build();
    return effects().updateState(newState);
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState> onLedgerEntryPaymentAdded(
    LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState state, LedgerEntryEntity.LedgerEntryPaymentAdded ledgerEntryPaymentAdded) {
    return effects().updateState(state.toBuilder().setPaymentId(ledgerEntryPaymentAdded.getPaymentId()).build());
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState> ignoreOtherEvents(
    LedgerEntriesByTransactionModel.LedgerEntriesByTransactionViewState state, Any any) {
    return effects().ignore();
  }
}

