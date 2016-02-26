import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;
import java.util.*;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      HashMap<String, Object>model= new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model,layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String stylistName = request.queryParams("stylistName");
      Stylist newStylist = new Stylist (stylistName);
      newStylist.save();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id =Integer.parseInt(request.params(":id"));
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      List<Client> clients = stylist.viewClients();
      model.put("id", id);
      model.put("stylist", stylist);
      model.put("clients", clients);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response ) -> {
      HashMap<String, Object>model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("clientName");
      int stylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client newClient = new Client(clientName, stylistId);
      newClient.save();
      model.put("clients", Client.all());
      model.put("template", "templates/clients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/:id" , (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id =Integer.parseInt(request.params(":id"));
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("id", id);
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants/:id/updated", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      String newName = request.queryParams("update");
      client.update(newName);
      model.put("client", newName);
      model.put("template", "templates/update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants/:id/deleted", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      client.delete();
      model.put("template", "templates/delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
