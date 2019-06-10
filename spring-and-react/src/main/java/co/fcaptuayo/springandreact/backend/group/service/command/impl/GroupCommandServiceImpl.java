package co.fcaptuayo.springandreact.backend.group.service.command.impl;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import co.fcaptuayo.springandreact.backend.group.repository.command.GroupRepositoryCommand;
import co.fcaptuayo.springandreact.backend.group.service.command.GroupCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class GroupCommandServiceImpl implements GroupCommandService {

    @Autowired
    private GroupRepositoryCommand repositoryCommand;

    @Override
    public void deleteById(@NotNull Long id) {
        repositoryCommand.deleteById(id);
    }

    @Override
    public Group save(@NotNull @Valid Group group) {
        return repositoryCommand.save(group);
    }

}

