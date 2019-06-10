package co.fcaptuayo.springandreact.backend.group.repository.query;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepositoryQuery extends JpaRepository<Group, Long> {
    Group findByName(String name);
}
