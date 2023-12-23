import java.util.*;

public class HashMapFinal<K, V> {
  
  ArrayList<LinkedList<Entity>> list;// basically the arraylist upar wala part bana rahe h

  private int size = 0;//size ko 0 rakha

  private float lf = 0.5f;//Load factor 

  public HashMapFinal() {//constructor banaya
    list = new ArrayList<>();//yha pe memory allocation ho gyi pehle sirf declare kia tha 
    // at this place we are making an empty linked list at every index of the arraylist 
    for(int i=0; i < 10; i++) {//default size 10 rakhi h
      list.add(new LinkedList<>());
    }
  }

  private class Entity {
    K key;
    V value;

    public Entity(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  
  public void put(K key, V value) {
    int hash = Math.abs(key.hashCode() % list.size());//hashcode nikala key ka mod list.size kia taki range m aaye hash code

    LinkedList<Entity> entities = list.get(hash);
// list.get(hash) se index milega arraylist ka jaha key insert karenge , ab waha bana do ek linked list entities naam ki
    for (Entity entity : entities) {
      if(entity.key.equals(key)) {// if the key already exists updating its value
        entity.value = value;
        return;
      }
    }
// if the load factor is greater than the declared lf rehash i.e double the size of the arraylist
    if((float)(size) / list.size() > lf) {//size is basically the total no of keys in the list
      reHash();
    }
    
    entities.add(new Entity(key, value));

    size++;//increasing the size after insertion
  }

  private void reHash() {
    System.out.println("We are now rehashing!");

    ArrayList<LinkedList<Entity>> old = list;// ek "old" naam ki arraylist banayi jo ki list k brabar h
    list = new ArrayList<>(); // ek nayi list bnayi 

    size = 0;

    for(int i=0; i<old.size() * 2; i++) {// purani size se dugni badi aur usme sab me banayi ek ll
      list.add(new LinkedList<>());
    }

    for(LinkedList<Entity> entries :old) {//arraylist ki har ek index ki ll pe loop 
      for(Entity entry : entries) {// har ek entity of ll of that index pe loop
        put(entry.key, entry.value); // list m add kar dia
      }
    }
  }

  public V get(K key) {
    int hash = Math.abs(key.hashCode() % list.size());
    LinkedList<Entity> entities = list.get(hash);
    for(Entity entity : entities) {
      if(entity.key.equals(key)) {
        return entity.value;
      }
    }
    return null;
  }

  public void remove(K key) {
    int hash = Math.abs(key.hashCode() % list.size());
    LinkedList<Entity> entities = list.get(hash);

    Entity target = null;
    
    for(Entity entity : entities) {
      if(entity.key.equals(key)) {
        target = entity;
        break;
      }
    }

    entities.remove(target);
    size--;
  }

  public boolean containsKey(K key) {
    return get(key) != null;
  }

  @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
        builder.append("{");
      for(LinkedList<Entity> entities : list) {
        for(Entity entity : entities) {
          builder.append(entity.key);
          builder.append(" = ");
          builder.append(entity.value);
          builder.append(" , ");
        }
      }
      builder.append("}");
      return builder.toString();
    } 
}


