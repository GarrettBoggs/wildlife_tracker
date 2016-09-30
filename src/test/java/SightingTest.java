import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;


public class SightingTest {

  public void sighting_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

}
