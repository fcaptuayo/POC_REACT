package co.fcaptuayo.springandreact.backend.group.service.query.impl;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import co.fcaptuayo.springandreact.backend.group.repository.query.GroupRepositoryQuery;
import co.fcaptuayo.springandreact.backend.group.service.query.GroupQueryService;
import co.fcaptuayo.springandreact.backend.util.page.param.LazyPageParamsRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class GroupQueryServiceImpl implements GroupQueryService {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer DEFAULT_SIZE = 100;

    private final Logger log = LoggerFactory.getLogger(GroupQueryServiceImpl.class);

    @Autowired
    private GroupRepositoryQuery repositoryQuery;

    @Override
    public Collection<Group> findAll(LazyPageParamsRest lazyPageParamsRest) {
        List<Group> target = new ArrayList<>();
        repositoryQuery.findAll(
                PageRequest.of(retrievePage(lazyPageParamsRest), retrieveSize(lazyPageParamsRest))
        ).forEach(target::add);
        return target;
    }

    @Override
    public Integer findAllCount(LazyPageParamsRest lazyPageParamsRest) {
        List<Group> target = new ArrayList<>();
        repositoryQuery.findAll().forEach(target::add);
        return target.size();
    }

    @Override
    public Group findById(Long id) throws EntityNotFoundException {
        Group response;
        Optional<Group> element = repositoryQuery.findById(id);
        if (element.isPresent()) {
            response = element.get();
        } else {
            throw new EntityNotFoundException("Error in retrieve Group by id");
        }
        return response;
    }

    private Integer retrievePage(LazyPageParamsRest lazyPageParamsRest) {
        Integer page = DEFAULT_PAGE;
        if (Objects.nonNull(lazyPageParamsRest) && Objects.nonNull(lazyPageParamsRest.getRows()) && Objects.nonNull(lazyPageParamsRest.getFirst())) {
            page = lazyPageParamsRest.getFirst() / lazyPageParamsRest.getRows();
        }
        log.info("page is {}", page );
        return page;
    }

    private Integer retrieveSize(LazyPageParamsRest lazyPageParamsRest) {
        Integer size = DEFAULT_SIZE;
        if (Objects.nonNull(lazyPageParamsRest) && Objects.nonNull(lazyPageParamsRest.getRows())) {
            size = lazyPageParamsRest.getRows();
        }
        return size;
    }
}
