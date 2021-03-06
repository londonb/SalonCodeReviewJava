import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Brad's Hair Salon");
  }

  @Test
  public void stylistIsCreated() {
    goTo ("http://localhost:4567/");
    fill("#stylistName").with("Brad");
    submit("a", withText("Add stylist"));
    assertThat(pageSource()).contains("Brad");
  }


  @Test
  public void clientIsCreated() {
    Stylist myStylist = new Stylist("Brad London");
    myStylist.save();
    Client client1 = new Client("CatBot", myStylist.getId());
    client1.save();
    Client client2 = new Client("Astro", myStylist.getId());
    client2.save();
    String clientPath = String.format("http://localhost:4567/clients");
    goTo(clientPath);
    assertThat(pageSource()).contains("CatBot");
    assertThat(pageSource()).contains("Astro");
  }

  @Test
  public void find_clientHasAPageForThemself() {
    Client newClient = new Client("Astro", 1);
    newClient.save();
    Client savedClient = Client.find(newClient.getId());
    String clientPath = String.format("http://localhost:4567/clients/%d", newClient.getId());
    goTo (clientPath);
    assertThat(pageSource()).contains("Astro");
  }

  @Test
  public void update_updatesClientInDatabased() {
    Client newClient = new Client("RoboCat", 1);
    newClient.save();
    newClient.update("RoboKitty");
    String clientPath = String.format("http://localhost:4567/clients", newClient.getId());
    goTo(clientPath);
    assertThat(pageSource()).contains("RoboKitty");
  }

  @Test
  public void delete_deletesClientsFromDatabase() {
    Client myClient = new Client ("RoboKitty", 1);
    myClient.save();
    myClient.delete();
    String clientPath = String.format("http://localhost:4567/clients");
    goTo(clientPath);
    assertThat(pageSource()).doesNotContain("RoboKitty");
  }


}
