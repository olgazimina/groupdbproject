drop table system.persons;

create table system.persons
(
    person_id integer not null,
    person    blob    not null,
    PRIMARY KEY(person_id)
);
alter table system.persons
    add constraint person_json
        check ( person is json );