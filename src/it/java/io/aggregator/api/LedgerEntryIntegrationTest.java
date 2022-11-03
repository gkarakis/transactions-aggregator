package io.aggregator.api;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import io.aggregator.Main;
import io.aggregator.entity.TransactionMerchantKey;
import kalix.javasdk.Kalix;
import kalix.javasdk.testkit.KalixTestKit;
import kalix.javasdk.testkit.junit.jupiter.KalixDescriptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Kalix proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
public class LedgerEntryIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @KalixDescriptor
  public static final Kalix kalix = Main.createKalix();
  public static final KalixTestKit testKit = new KalixTestKit(kalix);

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
   */
  private final LedgerEntry client;

  public LedgerEntryIntegrationTest() {
    client = testKit.getGrpcClient(LedgerEntry.class);
  }

  @Test
  @Disabled("to be implemented")
  public void createLedgerEntryOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.createLedgerEntry(LedgerEntryApi.CreateLedgerEntryCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  @Disabled("to be implemented")
  public void addPaymentOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.addPayment(LedgerEntryApi.AddPaymentCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  @Disabled("to be implemented")
  public void getLedgerEntryOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.getLedgerEntry(LedgerEntryApi.GetLedgerEntryRequest.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @org.junit.jupiter.api.Test
  public void happyPath() throws Exception {
    String incidentAmount = "100";
    String paymentId = "1234";
    String merchantId = "merchant1";
    String shopId = "merchant1Shop1";

    Timestamp t = Timestamps.fromDate(new Date());

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
        .setTimestamp(t)
        .setMerchantId(merchantId)
        .setShopId(shopId)
        .build();
    var createResponse = client.createLedgerEntry(createCommand).toCompletableFuture().get(5,SECONDS);

    var getCommand = LedgerEntryApi.GetLedgerEntryRequest.newBuilder()
        .setTransactionId(key.getTransactionId())
        .setServiceCode(key.getServiceCode())
        .setAccountFrom(key.getAccountFrom())
        .setAccountTo(key.getAccountTo())
        .build();
    var get = client.getLedgerEntry(getCommand).toCompletableFuture().get(5,SECONDS);

    assertEquals(incidentAmount, get.getAmount());
    assertTrue(get.getPaymentId().isEmpty());
    var addPaymentCommand = LedgerEntryApi.AddPaymentCommand.newBuilder()
        .setTransactionId(key.getTransactionId())
        .setServiceCode(key.getServiceCode())
        .setAccountFrom(key.getAccountFrom())
        .setAccountTo(key.getAccountTo())
        .setPaymentId(paymentId)
        .setTimestamp(t).build();
    var addPaymentResponse = client.addPayment(addPaymentCommand).toCompletableFuture().get(5,SECONDS);
    get = client.getLedgerEntry(getCommand).toCompletableFuture().get(5,SECONDS);
    assertEquals(incidentAmount, get.getAmount());
    assertEquals(paymentId, get.getPaymentId());

  }
}
