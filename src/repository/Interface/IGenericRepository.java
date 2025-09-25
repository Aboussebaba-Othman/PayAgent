package repository.Interface;

import java.util.List;
import java.util.Optional;

public interface IGenericRepository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T entity);
    boolean deleteById(ID id);
    boolean existsById(ID id);
    long count();
}