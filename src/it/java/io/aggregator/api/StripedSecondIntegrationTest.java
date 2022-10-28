package io.aggregator.api;

import io.aggregator.Main;
import kalix.javasdk.Kalix;
import kalix.javasdk.testkit.KalixTestKit;
import kalix.javasdk.testkit.junit.jupiter.KalixDescriptor;
import kalix.javasdk.testkit.junit.jupiter.KalixTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

// Example of an integration test calling our service via the Kalix proxy
// Run all test classes ending with "IntegrationTest" using `mvn verify -Pit`
@KalixTest
public class StripedSecondIntegrationTest {

  /**
   * The test kit starts both the service container and the Kalix proxy.
   */
  @KalixDescriptor
  public static final Kalix kalix = Main.createKalix();
  public static final KalixTestKit testKit = new KalixTestKit(kalix);

  /**
   * Use the generated gRPC client to call the service through the Kalix proxy.
   */
  private final StripedSecond client;

  public StripedSecondIntegrationTest() {
    testKit.start();
    client = testKit.getGrpcClient(StripedSecond.class);
  }

  @Test
  @Disabled("to be implemented")
  public void addLedgerItemsOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.addLedgerItems(StripedSecondApi.AddLedgerItemsCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }

  @Test
  @Disabled("to be implemented")
  public void aggregateStripedSecondOnNonExistingEntity() throws Exception {
    // TODO: set fields in command, and provide assertions to match replies
    // client.aggregateStripedSecond(StripedSecondApi.AggregateStripedSecondCommand.newBuilder().build())
    //         .toCompletableFuture().get(5, SECONDS);
  }
}
