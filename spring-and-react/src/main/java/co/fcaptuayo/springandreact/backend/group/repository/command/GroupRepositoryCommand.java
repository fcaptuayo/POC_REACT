package co.fcaptuayo.springandreact.backend.group.repository.command;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepositoryCommand extends CrudRepository<Group, Long> {
}
