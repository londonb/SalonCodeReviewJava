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
}
