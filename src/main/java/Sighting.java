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

  public Sighting(String location, String rangerName, Timestamp sightTime, int animalid){
    this.location = location;
    this.rangerName = rangerName;
    this.sightTime = sightTime;
    this.animalid = animalid;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (location, ranger_name, sight_time, animalid) VALUES (:location, :rangerName, now(), :animalid";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("rangerName", this.rangerName)
        .addParameter("animalid", this.animalid)
        .executeUpdate()
        .getKey();
    }
  }


}
