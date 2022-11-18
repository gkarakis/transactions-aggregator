package io.aggregator.view;

import com.google.protobuf.Any;
import io.aggregator.entity.LedgerEntryEntity;
import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the View Service described in your io/aggregator/view/ledger_entries_by_date.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LedgerEntriesByDateView extends AbstractLedgerEntriesByDateView {
  static final Logger log = LoggerFactory.getLogger(LedgerEntriesByDateView.class);

  public LedgerEntriesByDateView(ViewContext context) {}

  @Override
  public LedgerEntriesByDateModel.LedgerEntriesByDateViewState emptyState() {
    return LedgerEntriesByDateModel.LedgerEntriesByDateViewState.getDefaultInstance();
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByDateModel.LedgerEntriesByDateViewState> onLedgerEntryCreated(
      LedgerEntriesByDateModel.LedgerEntriesByDateViewState state, LedgerEntryEntity.LedgerEntryCreated ledgerEntryCreated) {
    log.debug("state: {}\nLedgerEntryCreated: {}", state, ledgerEntryCreated);
    log.info(Thread.currentThread().getName() + " - RECEIVED EVENT: LedgerEntryCreated");

    LedgerEntriesByDateModel.LedgerEntriesByDateViewState newState = LedgerEntriesByDateModel.LedgerEntriesByDateViewState.newBuilder()
        .setTransactionId(ledgerEntryCreated.getLedgerEntryKey().getTransactionId())
        .setServiceCode(ledgerEntryCreated.getLedgerEntryKey().getServiceCode())
        .setAccountFrom(ledgerEntryCreated.getLedgerEntryKey().getAccountFrom())
        .setAccountTo(ledgerEntryCreated.getLedgerEntryKey().getAccountTo())
        .setIncidentTimestamp(ledgerEntryCreated.getIncidentTimestamp())
        .setAmount(ledgerEntryCreated.getAmount())
        .setPaymentId("0")
        .setMerchantId(ledgerEntryCreated.getMerchantId())
        .setShopId(ledgerEntryCreated.getShopId())
        .setEventType(ledgerEntryCreated.getEventType())
        .build();
    return effects().updateState(newState);
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByDateModel.LedgerEntriesByDateViewState> onLedgerEntryPaymentAdded(
    LedgerEntriesByDateModel.LedgerEntriesByDateViewState state, LedgerEntryEntity.LedgerEntryPaymentAdded ledgerEntryPaymentAdded) {
    return effects().updateState(state.toBuilder().setPaymentId(ledgerEntryPaymentAdded.getPaymentId()).build());
  }

  @Override
  public View.UpdateEffect<LedgerEntriesByDateModel.LedgerEntriesByDateViewState> ignoreOtherEvents(
    LedgerEntriesByDateModel.LedgerEntriesByDateViewState state, Any any) {
    return effects().ignore();
  }
}

