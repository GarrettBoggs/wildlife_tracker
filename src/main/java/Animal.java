import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Animal extends Beast{

    public static final String DATABASE_TYPE = "animal";

    public Animal(String name){
      this.name = name;
      type = DATABASE_TYPE;
    }

    public static List<Animal> all() {
    String sql = "SELECT * FROM animals WHERE type='animal'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        if(name.equals("")){
          throw new UnsupportedOperationException("You need to name the animals!");
        }
        String sql = "INSERT INTO animals(name, type) VALUES (:name, :type)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("type", this.type)
          .executeUpdate()
          .getKey();
      }
    }

   public static Animal find(int id) {
       try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT * FROM animals where id=:id";
         Animal animal = con.createQuery(sql)
           .addParameter("id", id)
           .throwOnMappingFailure(false)
           .executeAndFetchFirst(Animal.class);
         return animal;
       }
     }

    public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE animals SET name = :name WHERE id = :id";
    con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
}
