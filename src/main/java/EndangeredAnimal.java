import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class EndangeredAnimal extends Beast {
  private String health;
  private String age;

  public static final String HEALTHY = "healthy";
  public static final String SICK = "sick";
  public static final String DECENT = "decent";

  public static final String YOUNG = "young";
  public static final String NEWBORN = "newborn";
  public static final String ADULT = "adult";

  public static final String DATABASE_TYPE = "endangeredanimal";

  public EndangeredAnimal(String name, String health, String age) {
    this.name = name;
    this.health = health;
    this.age = age;
    type = DATABASE_TYPE;

  }

  public String getHealth(){
    return health;
  }

  public String getAge(){
    return age;
  }

  public static List<EndangeredAnimal> all() {
  String sql = "SELECT * FROM animals WHERE type='endangeredanimal'";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql)
    .throwOnMappingFailure(false)
    .executeAndFetch(EndangeredAnimal.class);
    }
  }

  public static EndangeredAnimal find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM animals where id=:id";
        EndangeredAnimal animal = con.createQuery(sql)
          .addParameter("id", id)
          .throwOnMappingFailure(false)
          .executeAndFetchFirst(EndangeredAnimal.class);
        return animal;
      }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        if(name.equals(""))
        {
          throw new UnsupportedOperationException("You need to name the animals!");
        }
        String sql = "INSERT INTO animals(name, type, health, age) VALUES (:name, :type, :health, :age)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("type", this.type)
          .addParameter("health", this.health)
          .addParameter("age", this.age)
          .executeUpdate()
          .getKey();
      }
    }

    public void update(String name, String health, String age){
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE animals SET name = :name , health = :health , age = :age WHERE id=:id";
           con.createQuery(sql)
          .addParameter("name", name)
          .addParameter("health", health)
          .addParameter("age", age)
          .addParameter("id", id)
          .executeUpdate();
        }
    }

}
