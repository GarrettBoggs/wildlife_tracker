import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;

public class Sighting {

  private int id;
  private String location;
  private String rangerName;
  private Timestamp sightTime;
  private int animalid;

  public Sighting(String location, String rangerName, int animalid){
    this.location = location;
    this.rangerName = rangerName;
    this.animalid = animalid;
  }

  public int getId(){
    return id;
  }

  public String getLocation(){
    return location;
  }

  public String getRangerName(){
    return rangerName;
  }

  public int getAnimalId(){
    return animalid;
  }

  public Timestamp getSightTime(){
    return sightTime;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings(location, animalid, ranger_name, sight_time) VALUES (:location, :animalid, :ranger_name, now() );";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("animalid", this.animalid)
        .addParameter("ranger_name", this.rangerName)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
    }
  }

    public static List<Sighting> all() {
     String sql = "SELECT * FROM sightings";
     try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Sighting.class);
     }
   }

   @Override
   public boolean equals(Object otherSighting){
     if (!(otherSighting instanceof Sighting)) {
       return false;
     } else {
       Sighting newSighting = (Sighting) otherSighting;
       return this.getLocation().equals(newSighting.getLocation()) &&
              this.getId() == newSighting.getId() &&
              this.getAnimalId() == newSighting.getAnimalId();
     }
   }

   public static Sighting find(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM sightings where id=:id";
       Sighting sighting = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Sighting.class);
       return sighting;
     }
   }
}
