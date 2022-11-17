package io.aggregator.action;

import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.aggregator.api.LedgerEntryApi;
import io.aggregator.entity.StripedSecondEntity;
import kalix.javasdk.action.ActionCreationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your io/aggregator/action/striped_second_to_ledger_entry_action.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class StripedSecondToLedgerEntryAction extends AbstractStripedSecondToLedgerEntryAction {
  static final Logger log = LoggerFactory.getLogger(StripedSecondToLedgerEntryAction.class);

  public StripedSecondToLedgerEntryAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<Empty> onStripedSecondLedgeringActivitiesAdded(StripedSecondEntity.StripedSecondLedgeringActivitiesAdded event) {
    log.debug(Thread.currentThread().getName() + " - StripedSecondLedgeringActivitiesAdded: {}", event);
    log.info(Thread.currentThread().getName() + " - ON EVENT: StripedSecondLedgerItemsAdded");

    List<CompletableFuture<Empty>> executes =
        event.getLedgeringActivityList().stream().map(ledgeringActivity ->
            components().ledgerEntry().createLedgerEntry(
                LedgerEntryApi.CreateLedgerEntryCommand.newBuilder()
                    .setTransactionId(ledgeringActivity.getLedgeringActivityKey().getTransactionId())
                    .setMerchantId(event.getMerchantKey().getMerchantId())
                    .setShopId(event.getShopId())
                    .setServiceCode(ledgeringActivity.getLedgeringActivityKey().getServiceCode())
                    .setAccountFrom(ledgeringActivity.getLedgeringActivityKey().getAccountFrom())
                    .setAccountTo(ledgeringActivity.getLedgeringActivityKey().getAccountTo())
                    .setAmount(ledgeringActivity.getAmount())
                    .setTimestamp(event.getTimestamp())
                    .setEventType(event.getEventType())
                    .build()
            ).execute().toCompletableFuture()
        ).collect(Collectors.toList());
    CompletionStage<Empty> executesAggregation = allOf(executes).thenApply(list-> Empty.getDefaultInstance());
    return effects().asyncReply(executesAggregation);
  }

  @Override
  public Effect<Empty> onStripedSecondAggregated(StripedSecondEntity.StripedSecondAggregated event) {
    log.debug(Thread.currentThread().getName() + " - StripedSecondAggregated: {}", event);
    log.info(Thread.currentThread().getName() + " - ON EVENT: StripedSecondAggregated");

    List<CompletableFuture<Empty>> executes =
        event.getMoneyMovementsList().stream().map(moneyMovement ->
            components().ledgerEntry().addPayment(
                LedgerEntryApi.AddPaymentCommand.newBuilder()
                    .setTransactionId(moneyMovement.getTransactionId())
                    .setServiceCode(moneyMovement.getServiceCode())
                    .setAccountFrom(moneyMovement.getAccountFrom())
                    .setAccountTo(moneyMovement.getAccountTo())
                    .setPaymentId(event.getPaymentId())
                    .setTimestamp(event.getLastUpdateTimestamp())
                    .build()
            ).execute().toCompletableFuture()
        ).collect(Collectors.toList());
    CompletionStage<Empty> executesAggregation = allOf(executes).thenApply(list-> Empty.getDefaultInstance());
    return effects().asyncReply(executesAggregation);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }

  public <T> CompletableFuture<List<T>> allOf(List<CompletableFuture<T>> futuresList) {
    CompletableFuture<Void> allFuturesResult =
        CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
    return allFuturesResult.thenApply(v ->
        futuresList.stream().
            map(CompletableFuture::join).
            collect(Collectors.<T>toList())
    );
  }
}
