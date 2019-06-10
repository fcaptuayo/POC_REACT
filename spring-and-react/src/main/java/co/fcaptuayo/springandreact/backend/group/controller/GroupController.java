package co.fcaptuayo.springandreact.backend.group.controller;

import co.fcaptuayo.springandreact.backend.group.model.Group;
import co.fcaptuayo.springandreact.backend.group.repository.command.GroupRepositoryCommand;
import co.fcaptuayo.springandreact.backend.group.repository.query.GroupRepositoryQuery;
import co.fcaptuayo.springandreact.backend.group.service.command.GroupCommandService;
import co.fcaptuayo.springandreact.backend.group.service.query.GroupQueryService;
import co.fcaptuayo.springandreact.backend.util.page.param.LazyPageParamsRest;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Collection;

@RestController
@RequestMapping("/api-rest")
class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupCommandService serviceCommand;

    @Autowired
    private GroupQueryService serviceQuery;

    @GetMapping("/groups")
    Collection<Group> groups() {
        log.info("Request to findAll whit service /groups.");
        return this.retrieveGroups();
    }

    @GetMapping("/group/{id}")
    ResponseEntity<?> findGroupById(@PathVariable Long id) {
        log.info("Request to findGroupById:");
        return retrieveGroupById(id);
    }

    @PostMapping("/group")
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        return processCreateGroup(group);
    }

    @PutMapping("/group")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("Request to update group: {}", group);
        return processUpdateGroup(group);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("Request to delete group: {}", id);
        return processDeleteGroup(id);
    }

    @GetMapping("/paginator/groups/")
    public ResponseEntity<?> getAllWithFilter(@RequestParam(required = false, value = "filter") String filter) {
        log.info("Request to findAll whit service /paginator/groups/.");
        return retrieveGroupsWithFilter(filter);
    }


    private ResponseEntity<Group> processUpdateGroup(Group group) {
        ResponseEntity response;
        try {
            serviceQuery.findById(group.getId());
            response = ResponseEntity.ok().body(serviceCommand.save(group));
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    private ResponseEntity<?> processDeleteGroup(Long id) {
        ResponseEntity response;
        try {
            serviceQuery.findById(id);
            response = ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    private ResponseEntity<Group> processCreateGroup(Group group) throws URISyntaxException {
        Group result = serviceCommand.save(group);
        return ResponseEntity.created(new URI("/api-rest/group/" + result.getId()))
                .body(result);
    }

    private ResponseEntity<?> retrieveGroupById(Long id) {
        ResponseEntity response;
        try {
            response = ResponseEntity.ok().body(serviceQuery.findById(id));
        } catch (EntityNotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    private Collection<Group> retrieveGroups() {
        return serviceQuery.findAll(null);
    }


    private ResponseEntity<?> retrieveGroupsWithFilter(String filter) {
        Collection<Group> response;
        Integer totalRows;
        HttpHeaders headers = new HttpHeaders();
        if (filter != null) {
            LazyPageParamsRest lazyPageParamsRest = new Gson().fromJson(new String(Base64.getDecoder().decode(filter)), LazyPageParamsRest.class);
            log.info("Filter {}:", lazyPageParamsRest.toString());;
            response = serviceQuery.findAll(lazyPageParamsRest);
            totalRows = serviceQuery.findAllCount(lazyPageParamsRest);
        } else {
            response = serviceQuery.findAll(null);
            totalRows = serviceQuery.findAllCount(null);
        }
        headers.add("X-Result-Count", String.valueOf(totalRows));
        headers.add("Access-Control-Expose-Headers", "X-Result-Count");
        return ResponseEntity.ok().headers(headers).body(response);
    }
}
