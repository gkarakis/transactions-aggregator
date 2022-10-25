package io.aggregator.api;

import io.aggregator.Main;
import io.aggregator.TimeTo;
import kalix.javasdk.testkit.junit.KalixTestKitResource;
import org.junit.ClassRule;
import org.junit.Test;

import java.time.Instant;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Kalix proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class TransactionIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @ClassRule
  public static final KalixTestKitResource testKit =
    new KalixTestKitResource(Main.createKalix());

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
   */
  private final Transaction client;

  public TransactionIntegrationTest() {
    client = testKit.getGrpcClient(Transaction.class);
  }

  @Test
  public void createTransactionOnNonExistingEntity() throws Exception {
    TransactionApi.PaymentPricedCommand command = TransactionApi.PaymentPricedCommand.newBuilder()
        .setTransactionId("txn1")
        .setEventType("authorized")
        .setShopId("tesco-chelsea")
        .setTimestamp(TimeTo.fromEpochSecond(Instant.parse("2022-10-17T10:15:30.00Z").getEpochSecond()).toTimestamp())
        .addPricedItem(TransactionApi.PricedItem.newBuilder()
            .setServiceCode("SVC1")
            .setPricedItemAmount("1.11")
            .build())
        .build();
    client.paymentPriced(command);

    TransactionApi.PaymentPricedCommand command2 = TransactionApi.PaymentPricedCommand.newBuilder()
        .setTransactionId("txn1")
        .setEventType("approved")
        .setShopId("tesco-chelsea")
        .setTimestamp(TimeTo.fromEpochSecond(Instant.parse("2022-10-17T10:15:40.00Z").getEpochSecond()).toTimestamp())
        .addPricedItem(TransactionApi.PricedItem.newBuilder()
            .setServiceCode("SVC2")
            .setPricedItemAmount("2.22")
            .build())
        .build();
    client.paymentPriced(command2);

    TransactionApi.PaymentPricedCommand command3 = TransactionApi.PaymentPricedCommand.newBuilder()
        .setTransactionId("txn1")
        .setEventType("cleared")
        .setShopId("tesco-chelsea")
        .setTimestamp(TimeTo.fromEpochSecond(Instant.parse("2022-10-17T11:40:00.00Z").getEpochSecond()).toTimestamp())
        .addPricedItem(TransactionApi.PricedItem.newBuilder()
            .setServiceCode("SVC3")
            .setPricedItemAmount("3.33")
            .build())
        .build();
    client.paymentPriced(command3);

    TransactionApi.PaymentPricedCommand command4 = TransactionApi.PaymentPricedCommand.newBuilder()
        .setTransactionId("txn1")
        .setEventType("settled")
        .setShopId("tesco-chelsea")
        .setTimestamp(TimeTo.fromEpochSecond(Instant.parse("2022-10-17T16:27:50.00Z").getEpochSecond()).toTimestamp())
        .addPricedItem(TransactionApi.PricedItem.newBuilder()
            .setServiceCode("SVC4")
            .setPricedItemAmount("4.44")
            .build())
        .build();
    client.paymentPriced(command4);

    Thread.sleep(10000);

    TransactionApi.GetTransactionResponse transactionResponse = client.getTransaction(TransactionApi.GetTransactionRequest.newBuilder()
        .setTransactionId("txn1")
        .build()).toCompletableFuture().get(5, SECONDS);

    assertNotNull(transactionResponse);
    assertEquals("txn1", transactionResponse.getTransactionId());
    assertEquals("tesco-chelsea", transactionResponse.getShopId());
    assertEquals(4, transactionResponse.getTransactionIncidentCount());
  }
}
