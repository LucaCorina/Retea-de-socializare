package socialnetwork.repository.memory;

import socialnetwork.domain.Entitate;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entitate<ID>> implements Repository<ID, E> {

    private Validator<E> validator;
    Map<ID, E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<ID, E>();
    }

    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the given id
     */
    @Override
    public E findOne(ID id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    /**
     * @return all entitys
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * Adds the entity
     *
     * @param entity entity must be not null
     * @return entity
     */
    @Override
    public E save(E entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if (entities.get(entity.getId()) != null) {
            throw new IllegalArgumentException("entity with given id already exists");
        } else entities.put(entity.getId(), entity);
        return null;
    }

    /**
     * Deletes the entity with the given id
     *
     * @param id id must be not null
     */
    @Override
    public void delete(ID id) {
        if (entities.get(id) == null) throw new IllegalArgumentException("entity with given id dose not exist");
        entities.remove(id);
    }

    /**
     * Updates an entity
     *
     * @param entity entity must not be null
     */
    @Override
    public void update(E entity) {

        if (entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(), entity);

        if (entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
        }

    }

    @Override
    public void registerUser(E entity, String u, String p, String s) {

    }

    @Override
    public int validateLogin(String u, String p) {
        return 0;
    }

    @Override
    public E findByUser_Name(String u) {
        return null;
    }

}
