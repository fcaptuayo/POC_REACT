package co.fcaptuayo.springandreact.backend.group.service.query;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import co.fcaptuayo.springandreact.backend.util.page.param.LazyPageParamsRest;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;


public interface GroupQueryService {

    Collection<Group> findAll(LazyPageParamsRest lazyPageParamsRest);

    Integer findAllCount(LazyPageParamsRest lazyPageParamsRest);

    Group findById(Long id) throws EntityNotFoundException;

}
