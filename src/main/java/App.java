import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      model.put("animals", Animal.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/Animal-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("animal");
      try{
        Animal newAnimal = new Animal(name);
        newAnimal.save();
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("animals", Animal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/animal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));

      model.put("animal", animal);
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));

      String location = request.queryParams("location");
      String rangerName = request.queryParams("name");
      Sighting newSighting = new Sighting(location, rangerName, animal.getId());
      try{
        newSighting.save();
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("sightings", animal.getSightings());
      model.put("animal", animal);
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

}
