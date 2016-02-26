import org.sql2o.*;
import java.util.List;
import java.util.*;

public class Stylist {
  private String stylist_name;
  private int id;

  public Stylist(String stylist_name) {
    this.stylist_name = stylist_name;
  }

  public int getId() {
    return id;
  }

  public String getStylistName() {
    return stylist_name;
  }

  @Override
  public boolean equals(Object otherStylist){
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getStylistName().equals(newStylist.getStylistName()) &&
        this.getId() == newStylist.getId();
    }
  }

  public static List<Stylist> all() {
    String sql = "SELECT * FROM stylists";
      try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Stylist.class);
      }
  }

}
