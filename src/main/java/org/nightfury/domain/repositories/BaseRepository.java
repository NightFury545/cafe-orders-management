package org.nightfury.domain.repositories;

import java.util.List;
import java.util.Optional;
import org.nightfury.shared.Result;

public interface BaseRepository<Entity, ID> {
    Result<Entity> save(Entity order);
    Optional<Entity> findById(ID id);
    List<Entity> findAll();
    Result<Void> deleteById(ID id);
}
