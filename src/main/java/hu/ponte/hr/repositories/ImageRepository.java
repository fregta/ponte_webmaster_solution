package hu.ponte.hr.repositories;

import hu.ponte.hr.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
