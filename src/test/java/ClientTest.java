import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import org.junit.Rule;
import java.util.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfClientsAreTheSame() {
    Client client1 = new Client ("Brad", 1);
    Client client2 = new Client ("Brad", 1);
    assertTrue(client1.equals(client2));
  }

  @Test
  public void save_savesClientsToDatabase() {
    Client newClient = new Client("Brad", 1);
    newClient.save();
    assertTrue(Client.all().get(0).equals(newClient));
  }

  @Test
  public void find_findsClientInDatabase() {
    Client newClient = new Client("Brad", 1);
    newClient.save();
    Client savedClient = Client.find(newClient.getId());
    assertEquals(savedClient.getClientName(), "Brad");
  }

  @Test
  public void update_updatesClientNameInDatabase() {
    Client newClient = new Client ("Bradd", 1);
    newClient.save();
    newClient.update("Brad");
    assertEquals(newClient.getClientName(), "Brad");
  }
}
