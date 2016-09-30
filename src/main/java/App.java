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
      model.put("endangeredAnimals", EndangeredAnimal.all());
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
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/EndangeredAnimal-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("animal");
      int inputHealth = Integer.parseInt(request.queryParams("health"));
      int inputAge = Integer.parseInt(request.queryParams("age"));
      String realHealth = "";
      String realAge = "";
      if(inputHealth == 1){
        realHealth = EndangeredAnimal.HEALTHY;
      }
      else if(inputHealth == 2) {
        realHealth = EndangeredAnimal.DECENT;
      }
      else {
        realHealth = EndangeredAnimal.SICK;
      }

      if(inputAge == 1){
        realAge = EndangeredAnimal.NEWBORN;
      }
      else if(inputAge == 2) {
        realAge = EndangeredAnimal.YOUNG;
      }
      else {
        realAge = EndangeredAnimal.ADULT;
      }
      EndangeredAnimal newAnimal = new EndangeredAnimal(name, realHealth, realAge);
      try{
        newAnimal.save();
        newAnimal.update();
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
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

    get("/endangeredanimal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));

      model.put("animal", animal);
      model.put("template", "templates/endangeredanimal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/endangeredanimal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));

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
      model.put("template", "templates/endangeredanimal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }

}
