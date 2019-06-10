package co.fcaptuayo.springandreact.backend.group.service.command;

import co.fcaptuayo.springandreact.backend.group.model.Group;

public interface GroupCommandService {

    void deleteById(Long id);

    Group save(Group group);

}

