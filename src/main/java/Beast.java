import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public abstract class Beast {
    public int id;
    public String name;
    public String type;

    public String getName(){
      return name;
    }

    public int getId() {
      return id;
    }

   public List<Sighting> getSightings() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM sightings where animalId=:id";
       return con.createQuery(sql)
         .addParameter("id", this.id)
         .executeAndFetch(Sighting.class);
     }
   }

   @Override
   public boolean equals(Object otherAnimal) {
     if (!(otherAnimal instanceof Beast)) {
       return false;
     } else {
       Beast newAnimal = (Beast) otherAnimal;
       return this.getName().equals(newAnimal.getName()) &&
              this.getId() == newAnimal.getId();
     }
   }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    }

  }
