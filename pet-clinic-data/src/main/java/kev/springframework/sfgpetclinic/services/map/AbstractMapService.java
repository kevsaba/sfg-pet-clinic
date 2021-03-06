package kev.springframework.sfgpetclinic.services.map;

import kev.springframework.sfgpetclinic.model.BaseEntity;
import kev.springframework.sfgpetclinic.model.Person;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T t) {
        if (t != null) {
            if (t.getId() == null) {
                t.setId(getNextId());
            }
            map.putIfAbsent(t.getId(), t);
            return t;
        } else {
            throw new RuntimeException("Object cant be null");
        }
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T t) {
        map.entrySet().removeIf(e -> e.getValue().equals(t));
    }

    private long getNextId() {
        long nextId;
        try {
            nextId = Collections.max(map.keySet()) + 1;
        } catch (NoSuchElementException e) {
            nextId = 1L;
        }
        return nextId;
    }

    protected T findByName(String text) {
        for (T val : map.values()) {
            if (val instanceof Person) {
                var p = (Person) val;
                if (p.getFirstName().toLowerCase(Locale.ROOT).equals(text.toLowerCase(Locale.ROOT))) {
                    return val;
                }
            }
        }
        return null;
    }
}
