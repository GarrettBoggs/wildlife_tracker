import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_true() {
    Animal testAnimal = new Animal("Deer");
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getName_animalInstantiatesWithName_Deer() {
    Animal testAnimal = new Animal("Deer");
    assertEquals("Deer", testAnimal.getName());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
   Animal firstAnimal = new Animal("Deer");
   firstAnimal.save();
   Animal secondAnimal = new Animal("ZomBeaver");
   secondAnimal.save();
   assertEquals(true, Animal.all().get(0).equals(firstAnimal));
   assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
   Animal testAnimal = new Animal("PyroBeaver");
   testAnimal.save();
   assertTrue(testAnimal.getId() > 0);
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
   Animal firstAnimal = new Animal("Cyclops");
   firstAnimal.save();
   Animal secondAnimal = new Animal("Work");
   secondAnimal.save();
   assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Animal firstAnimal = new Animal("Bear");
    Animal secondAnimal = new Animal("Bear");
    assertTrue(firstAnimal.equals(secondAnimal));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Animal myAnimal = new Animal("FlameDuck");
    myAnimal.save();
    assertTrue(Animal.all().get(0).equals(myAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    Animal myAnimal = new Animal("ZombieDragon");
    myAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(myAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void delete_deletesAnimal_true() {
    Animal myAnimal = new Animal("Fish");
    myAnimal.save();
    int myAnimalId = myAnimal.getId();
    myAnimal.delete();
    assertEquals(null, Animal.find(myAnimalId));
  }

  @Test
  public void getSightings_retrievesALlSightingsFromDatabase_tasksList() {
    Animal myAnimal = new Animal("Elk");
    myAnimal.save();
    Sighting firstSighting = new Sighting("Volcano", "Chill Garrett", myAnimal.getId());
    firstSighting.save();
    Sighting secondSighting = new Sighting("River", "Garrett the Conquerer", myAnimal.getId());
    secondSighting.save();
    Sighting[] tasks = new Sighting[] { firstSighting, secondSighting };
    assertTrue(myAnimal.getSightings().containsAll(Arrays.asList(tasks)));
  }


}
