package io.aggregator.api;

import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import io.aggregator.Main;
import io.aggregator.entity.LedgerEntryEntity;
import io.aggregator.view.LedgerEntriesByDate;
import io.aggregator.view.LedgerEntriesByDateModel;
import kalix.javasdk.Kalix;
import kalix.javasdk.testkit.KalixTestKit;
import kalix.javasdk.testkit.junit.jupiter.KalixDescriptor;
import kalix.javasdk.testkit.junit.jupiter.KalixTest;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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
@KalixTest
public class LedgerEntryViewIntegrationTest {

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
  private final LedgerEntriesByDate view;

  public LedgerEntryViewIntegrationTest() {
    testKit.start();
    client = testKit.getGrpcClient(LedgerEntry.class);
    view = testKit.getGrpcClient(LedgerEntriesByDate.class);
  }

  @Test
  public void happyPath() throws Exception {
    String incidentAmount = "100";
    String paymentId = "1234";
    String merchantId = "merchant1";
    String shopId = "merchant1Shop1";

    Timestamp t = Timestamps.fromDate(new Date());

    LedgerEntryEntity.LedgerEntryKey key = LedgerEntryEntity.LedgerEntryKey.newBuilder()
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
    var createResponse = client.createLedgerEntry(createCommand).toCompletableFuture().get(15,SECONDS);

    var getCommand = LedgerEntryApi.GetLedgerEntryRequest.newBuilder()
            .setTransactionId(key.getTransactionId())
            .setServiceCode(key.getServiceCode())
            .setAccountFrom(key.getAccountFrom())
            .setAccountTo(key.getAccountTo())
            .build();
    var get = client.getLedgerEntry(getCommand).toCompletableFuture().get(15,SECONDS);

    assertEquals(incidentAmount, get.getAmount());
    assertTrue(get.getPaymentId().isEmpty());

    Thread.sleep(5000);

    Timestamp from = Timestamps.fromMillis(Instant.now().minusSeconds(1000).toEpochMilli());
    Timestamp to = Timestamps.fromMillis(Instant.now().toEpochMilli());
    var unPaidIncidentsByMerchantAndDateReq = LedgerEntriesByDateModel.LedgerEntriesByDateRequest.newBuilder()
            .setFromDate(from)
            .setToDate(to)
            .setMerchantId(merchantId)
            .setPaymentId("0")
            .build();
    var unPaidIncidentsByMerchantAndDateRes = view.getLedgerEntriesByDate(unPaidIncidentsByMerchantAndDateReq).toCompletableFuture().get(5,SECONDS);
    assertEquals(1,unPaidIncidentsByMerchantAndDateRes.getResultsCount());

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
    assertEquals(paymentId,get.getPaymentId());


    Thread.sleep(5000);

    //check
    unPaidIncidentsByMerchantAndDateRes = view.getLedgerEntriesByDate(unPaidIncidentsByMerchantAndDateReq).toCompletableFuture().get(5,SECONDS);
    assertEquals(0,unPaidIncidentsByMerchantAndDateRes.getResultsCount());

    var paidIncidentsByMerchantAndDateReq = unPaidIncidentsByMerchantAndDateReq
            .toBuilder()
            .setPaymentId(paymentId)
            .build();
    var paidIncidentsByMerchantAndDateRes = view.getLedgerEntriesByDate(paidIncidentsByMerchantAndDateReq).toCompletableFuture().get(5,SECONDS);
    assertEquals(1,paidIncidentsByMerchantAndDateRes.getResultsCount());
    assertEquals(paymentId, paidIncidentsByMerchantAndDateRes.getResults(0).getPaymentId());

  }

}
