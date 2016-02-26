import org.sql2o.*;
import java.util.List;
import java.util.*;

public class Stylist {
  private int id;
  private String stylist_name;

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
        return con.createQuery(sql)
        .executeAndFetch(Stylist.class);
      }
  }

  public void save() {
    String sql = "INSERT INTO stylists (stylist_name) VALUES (:stylist_name)";
      try (Connection con = DB.sql2o.open()) {
        this.id = (int) con.createQuery(sql, true)
        .addParameter("stylist_name", stylist_name)
        .executeUpdate()
        .getKey();
      }
  }

  public static Stylist find(int id) {
    String sql = "SELECT * FROM stylists WHERE id =:id";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      }
  }

  public void update(String newStylistName) {
    this.stylist_name = newStylistName;
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE stylists SET stylist_name=:stylist_name WHERE id=:id";
        con.createQuery(sql)
        .addParameter("stylist_name", newStylistName)
        .addParameter("id", id)
        .executeUpdate();
      }
  }

  public void delete() {
    try(Connection con =DB.sql2o.open()) {
      String sql = "DELETE FROM stylists WHERE id=:id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public List<Client> viewClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients WHERE stylist_id=:id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetch(Client.class);
    }
  }
}
