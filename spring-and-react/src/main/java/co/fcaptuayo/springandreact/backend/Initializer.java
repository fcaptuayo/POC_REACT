package co.fcaptuayo.springandreact.backend;

import co.fcaptuayo.springandreact.backend.group.model.Event;
import co.fcaptuayo.springandreact.backend.group.model.Group;
import co.fcaptuayo.springandreact.backend.group.repository.command.GroupRepositoryCommand;
import co.fcaptuayo.springandreact.backend.group.repository.query.GroupRepositoryQuery;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final GroupRepositoryQuery groupQuery;
    private final GroupRepositoryCommand groupCommand;

    public Initializer(GroupRepositoryQuery groupQuery, GroupRepositoryCommand command) {
        this.groupQuery = groupQuery;
        this.groupCommand = command;
    }

    @Override
    public void run(String... strings) {
        Stream.of(
                "GROUP_1",
                "GROUP_2",
                "GROUP_3",
                "GROUP_4",
                "GROUP_6",
                "GROUP_7",
                "GROUP_8",
                "GROUP_9",
                "GROUP_10",
                "GROUP_11",
                "GROUP_12",
                "GROUP_13",
                "GROUP_14",
                "GROUP_15",
                "GROUP_16",
                "GROUP_17",
                "GROUP_18",
                "GROUP_19",
                "GROUP_20",
                "GROUP_21",
                "GROUP_22",
                "GROUP_23",
                "GROUP_24"
                ).forEach(name ->
                groupCommand.save(new Group(name))
        );

        Group group3 = groupQuery.findByName("GROUP_3");

        Event e = Event
                .builder()
                .title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(
                        Instant.parse("2018-12-12T18:00:00.000Z")
                )
                .build();

        group3.setEvents(Collections.singleton(e));

        groupCommand.save(group3);
        groupQuery.findAll().forEach(System.out::println);

    }
}
