import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.DateFormat;
import java.sql.Timestamp;

public class SightingTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void sighting_instantiatesCorrectly_true() {
    Sighting testSighting = new Sighting("River", "Steve" , 1);
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void Sighting_instantiatesCorrectly_true() {
    Sighting mySighting = new Sighting("River", "Steve", 1);
    assertEquals(true, mySighting instanceof Sighting);
  }

  @Test
  public void Sighting_instantiatesWithLocation_String() {
    Sighting mySighting = new Sighting("River", "Steve", 1);
    assertEquals("River", mySighting.getLocation());
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Sighting testSighting = new Sighting("River", "Steve", 1);
    testSighting.save();
    Timestamp savedSite = Sighting.find(testSighting.getId()).getSightTime();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedSite.getDay());
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Sighting firstSighting = new Sighting("River", "Steve", 1);
    firstSighting.save();
    Sighting secondSighting = new Sighting("Volcano", "Zelda", 1);
    secondSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(firstSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondSighting));
  }

   @Test
   public void getId_tasksInstantiateWithAnID_1() {
     Sighting mySighting = new Sighting("River", "Steve", 1);
     mySighting.save();
     assertTrue(mySighting.getId() > 0);
   }

   @Test
   public void find_returnsSightingWithSameId_secondSighting() {
     Sighting firstSighting = new Sighting("River", "Steve", 1);
     firstSighting.save();
     Sighting secondSighting = new Sighting("Volcano", "Zelda", 1);
     secondSighting.save();
     assertEquals(Sighting.find(secondSighting.getId()), secondSighting);
   }

   @Test
   public void equals_returnsTrueIfLocationsAretheSame() {
     Sighting firstSighting = new Sighting("River", "Steve", 1);
     Sighting secondSighting = new Sighting("River", "Steve", 1);
     assertTrue(firstSighting.equals(secondSighting));
   }

   @Test
   public void save_returnsTrueIfLocationsAretheSame() {
     Sighting mySighting = new Sighting("River", "Steve", 1);
     mySighting.save();
     assertTrue(Sighting.all().get(0).equals(mySighting));
   }

   @Test
   public void save_assignsIdToObject() {
     Sighting mySighting = new Sighting("River", "Steve", 1);
     mySighting.save();
     Sighting savedSighting = Sighting.all().get(0);
     assertEquals(mySighting.getId(), savedSighting.getId());
   }

   @Test
   public void save_savesAnimalIdIntoDB_true() {
     Animal myAnimal = new Animal("Dragon");
     myAnimal.save();
     Sighting mySighting = new Sighting("River", "Steve", myAnimal.getId());
     mySighting.save();
     Sighting savedSighting = Sighting.find(mySighting.getId());
     assertEquals(savedSighting.getAnimalId(), myAnimal.getId());
   }

 }
