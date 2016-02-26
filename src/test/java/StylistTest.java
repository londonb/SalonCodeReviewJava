import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.*;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_checksIfStylistsTheSame() {
    Stylist stylist1 = new Stylist ("Brad");
    Stylist stylist2 = new Stylist ("Brad");
    assertTrue(stylist1.equals(stylist2));
  }

  @Test
  public void save_savesStylistToDatabase() {
    Stylist newStylist = new Stylist ("Brad");
    newStylist.save();
    assertTrue(Stylist.all().get(0).equals(newStylist));
  }
}
