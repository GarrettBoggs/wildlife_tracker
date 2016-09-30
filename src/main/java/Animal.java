import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Animal {
    private int id;
    private String name;

    public Animal(String name){
      this.name = name;
    }

    public String getName(){
      return name;
    }

    public static List<Animal> all() {
    String sql = "SELECT id, name FROM animals";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Animal.class);
      }
    }

    public int getId() {
      return id;
    }

   public static Animal find(int id) {
       try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT * FROM animals where id=:id";
         Animal animal = con.createQuery(sql)
           .addParameter("id", id)
           .executeAndFetchFirst(Animal.class);
         return animal;
       }
     }

   /* public List<Sighting> getSightings() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM sightings where animalId=:id";
       return con.createQuery(sql)
         .addParameter("id", this.id)
         .executeAndFetch(Sighting.class);
     }
   } */

   @Override
   public boolean equals(Object otherAnimal) {
     if (!(otherAnimal instanceof Animal)) {
       return false;
     } else {
       Animal newAnimal = (Animal) otherAnimal;
       return this.getName().equals(newAnimal.getName()) &&
              this.getId() == newAnimal.getId();
     }
   }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO animals(name) VALUES (:name)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .executeUpdate()
          .getKey();
      }
    }
  }
