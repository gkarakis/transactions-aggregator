package io.aggregator.action;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import kalix.javasdk.action.ActionCreationContext;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.aggregator.TimeTo;
import io.aggregator.action.FrontendService.GenerateTransactionsSingleMerchantRequest;
import io.aggregator.api.TransactionApi;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class FrontendAction extends AbstractFrontendAction {
  static final Random random = new Random();
  static final Logger log = LoggerFactory.getLogger(FrontendAction.class);

  public FrontendAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> generateTransactions(FrontendService.GenerateTransactionsRequest request) {
    log.info("generateTransactions: {}", request);

    var transactionIntervalMs = request.getTransactionIntervalMs();
    var intervalMs = transactionIntervalMs == 0 ? 10 : transactionIntervalMs;

    var results = IntStream.range(0, request.getTransactionCount())
        .mapToObj(i -> {
          var timestamp = TimeTo.fromTimestamp(request.getDay()).plus().milliSeconds(i * intervalMs).toTimestamp();

          return TransactionApi.PaymentPricedCommand
              .newBuilder()
              .setTransactionId(String.format("transaction-%d-%s", i, UUID.randomUUID()))
              .setShopId("tesco-chelsea")
              .addPricedItem(TransactionApi.PricedItem.newBuilder()
                  .setServiceCode("SVC" + random.nextInt(4) + 1)
                  .setPricedItemAmount(String.valueOf(random.nextInt(90) + 5))
                  .build())
              .setTimestamp(timestamp)
              .build();
        })
        .map(command -> components().transaction().paymentPriced(command).execute())
        .toList();

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> generateTransactionsSingleMerchant(GenerateTransactionsSingleMerchantRequest request) {
    log.info("generateTransactionsSingleMerchant: {}", request);

    var transactionIntervalMs = request.getTransactionIntervalMs();
    var intervalMs = transactionIntervalMs == 0 ? 10 : transactionIntervalMs;

    var results = IntStream.rangeClosed(request.getTransactionIdFirst(), request.getTransactionIdLast())
        .mapToObj(i -> {
          var timestamp = TimeTo.fromTimestamp(request.getDay()).plus().milliSeconds(i * intervalMs).toTimestamp();

          return TransactionApi.PaymentPricedCommand
              .newBuilder()
              .setTransactionId(String.format("transaction-%d-%s", i, UUID.randomUUID()))
//              .setMerchantId(request.getMerchantId())
//              .setServiceCode(request.getServiceCode())
//              .setAccountFrom(request.getAccountFrom())
//              .setAccountTo(request.getAccountTo())
//              .setTransactionAmount(random.nextInt(100) / 10.0)
              .setTimestamp(timestamp)
              .build();
        })
        .map(command -> components().transaction().paymentPriced(command).execute())
        .toList();

    var result = CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()]))
        .thenApply(reply -> effects().reply(Empty.getDefaultInstance()));

    return effects().asyncEffect(result);
  }
}
