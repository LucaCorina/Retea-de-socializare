package socialnetwork.repository;

import socialnetwork.domain.Entitate;
import socialnetwork.domain.validators.ValidationException;

/**
 * CRUD operations repository interface
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> -  type of entities saved in repository
 */

public interface Repository<ID, E extends Entitate<ID>> {

    /**
     *
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     *          or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     *                  if id is null.
     */
    E findOne(ID id);

    /**
     *
     * @return all entities
     */
    Iterable<E> findAll();

    /**
     *
     * @param entity
     *         entity must be not null
     * @return null- if the given entity is saved
     *         otherwise returns the entity (id already exists)
     * @throws ValidationException
     *            if the entity is not valid
     * @throws IllegalArgumentException
     *             if the given entity is null.     *
     */
    E save(E entity);


    /**
     *  removes the entity with the specified id
     * @param id
     *      id must be not null
     * @throws IllegalArgumentException
     *                   if the given id is null.
     */
    void delete(ID id);

    /**
     *
     * @param entity
     *          entity must not be null
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidationException
     *             if the entity is not valid.
     */
    void update(E entity);

    void registerUser(E entity, String u, String p, String s);

    int validateLogin(String u, String p);

    E findByUser_Name(String u);
}

