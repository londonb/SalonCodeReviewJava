import org.sql2o.*;
import java.util.List;
import java.util.*;

public class Client {
  private int id;
  private String client_name;
  private int stylist_id;

  public Client (String client_name, int stylist_id) {
    this.client_name = client_name;
    this.stylist_id = stylist_id;
  }

  public int getId() {
    return id;
  }

  public String getClientName() {
    return client_name;
  }

  public int getStylistID() {
    return stylist_id;
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getClientName().equals(newClient.getClientName()) &&
        this.getId() == newClient.getId();
    }
  }

  public static List<Client> all() {
    String sql = "SELECT * FROM clients";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Client.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO clients (client_name, stylist_id) VALUES (:client_name, :stylist_id)";
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
      .addParameter("client_name", client_name)
      .addParameter("stylist_id", stylist_id)
      .executeUpdate()
      .getKey();
    }
  }
}
