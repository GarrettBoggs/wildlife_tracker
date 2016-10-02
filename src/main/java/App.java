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

      model.put("sightings", animal.getSightings());
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

    post("/animal/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      try{
        animal.update(name);
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
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

    post("/endangeredanimal/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));
      String name = request.queryParams("name");
      String health = request.queryParams("health");
      String age = request.queryParams("age");
      try {
        animal.update(name, health, age);
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/endangeredanimal/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));

      model.put("sightings", animal.getSightings());
      model.put("animal", animal);
      model.put("template", "templates/endangeredanimal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animal/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      animal.delete();
      animal.deleteSightings();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/endangeredanimal/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));
      animal.delete();
      animal.deleteSightings();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sighting/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sighting sighting = Sighting.find(Integer.parseInt(request.params(":id")));

      model.put("sighting", sighting);
      model.put("template", "templates/sighting.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sighting/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sighting sighting = Sighting.find(Integer.parseInt(request.params(":id")));
      sighting.delete();

      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sighting/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sighting sighting = Sighting.find(Integer.parseInt(request.params(":id")));
      String location = request.queryParams("location");
      String name = request.queryParams("name");
      try{
        sighting.update(location,name);
      }
      catch (UnsupportedOperationException exception)
      { }
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


  }
}
