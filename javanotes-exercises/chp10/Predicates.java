import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// set up the generic class
public class Predicates {

  public static <Type> void remove(Collection<Type> collection, Predicate<Type> predicate) {
    Iterator<Type> iterator = collection.iterator();
    Type item;
    boolean toRemove;

    while (iterator.hasNext()) {
      item = iterator.next();
      toRemove = predicate.test(item);
      if (toRemove) {
        iterator.remove();
      }
    }

  } // end remove

  public static <Type> void retain(Collection<Type> collection, Predicate<Type> predicate) {
    Iterator<Type> iterator = collection.iterator();
    Type item;
    boolean toKeep;

    while (iterator.hasNext()) {
      item = iterator.next();
      toKeep = predicate.test(item);
      if (!toKeep) {
        iterator.remove();
      }
    }
  } // end retain

  public static <Type> ArrayList<Type> collect(Collection<Type> collection, Predicate<Type> predicate) {
    ArrayList<Type> list = new ArrayList<Type>();
    Iterator<Type> iterator = collection.iterator();
    Type item = null;
    boolean toCollect;

    while (iterator.hasNext()) {
      item = iterator.next();
      toCollect = predicate.test(item);
      if (toCollect) {
        list.add(item);
      }
    }
    return list;
  } // end collect

  public static <Type> int find(ArrayList<Type> list, Predicate<Type> predicate) {
    int index = -1;
    for (Type item : list) {
      if (predicate.test(item)) {
        index = list.indexOf(item);
        break;
      }
    }
    return index;
  } // end find

} // end class
