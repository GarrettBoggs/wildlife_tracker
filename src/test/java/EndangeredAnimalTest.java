import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class EndangeredAnimalTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

    @Test
  public void animal_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getName_animalInstantiatesWithName_Deer() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
    assertEquals("Deer", testEndangeredAnimal.getName());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
   EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
   firstEndangeredAnimal.save();
   EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("ZomBeaver", "healthy", "old");
   secondEndangeredAnimal.save();
   assertEquals(true, EndangeredAnimal.all().get(0).equals(firstEndangeredAnimal));
   assertEquals(true, EndangeredAnimal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
   EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
   testEndangeredAnimal.save();
   assertTrue(testEndangeredAnimal.getId() > 0);
  }

  @Test
  public void find_returnsEndangeredAnimalWithSameId_secondEndangeredAnimal() {
   EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Deer", "sick", "young");
   firstEndangeredAnimal.save();
   EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Fish", "decent","newborn");
   secondEndangeredAnimal.save();
   assertEquals(EndangeredAnimal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("Bear", "healthy", "young");
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("Bear", "healthy", "young");
    assertTrue(firstEndangeredAnimal.equals(secondEndangeredAnimal));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Elk", "healthy", "young");
    myEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.all().get(0).equals(myEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToObject() {
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Elk", "healthy", "young");
    myEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(myEndangeredAnimal.getId(), savedEndangeredAnimal.getId());
  }

  @Test
  public void delete_deletesEndangeredanimal_true() {
    EndangeredAnimal myAnimal = new EndangeredAnimal("Fish", "healthy", "young");
    myAnimal.save();
    int myAnimalId = myAnimal.getId();
    myAnimal.delete();
    assertEquals(null, EndangeredAnimal.find(myAnimalId));
  }

  @Test
  public void save_healthStoredInDatabase_true() {
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Elk", "healthy", "young");
    myEndangeredAnimal.save();
    EndangeredAnimal savedEndangeredAnimal = EndangeredAnimal.all().get(0);
    assertEquals(myEndangeredAnimal.getHealth(), savedEndangeredAnimal.getHealth());
    assertEquals(myEndangeredAnimal.getAge(), savedEndangeredAnimal.getAge());
  }

  @Test
  public void update_updatesEndangeredAnimal_true() {
    EndangeredAnimal myAnimal = new EndangeredAnimal("FlameDuck", EndangeredAnimal.HEALTHY, EndangeredAnimal.ADULT);
    myAnimal.save();
    myAnimal.update("PolarBear", EndangeredAnimal.SICK, EndangeredAnimal.NEWBORN);
    assertEquals("PolarBear", EndangeredAnimal.find(myAnimal.getId()).getName());
    assertEquals("sick", EndangeredAnimal.find(myAnimal.getId()).getHealth());
    assertEquals("newborn", EndangeredAnimal.find(myAnimal.getId()).getAge());
  }
}
