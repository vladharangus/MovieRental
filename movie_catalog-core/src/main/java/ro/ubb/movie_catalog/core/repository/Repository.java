package ro.ubb.movie_catalog.core.repository;
//

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.movie_catalog.core.domain.entities.BaseEntity;

import java.io.Serializable;

//
@NoRepositoryBean
public interface Repository<ID extends Serializable, T extends BaseEntity<ID>> extends JpaRepository<T, ID> {



}
