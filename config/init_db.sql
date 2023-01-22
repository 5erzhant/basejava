create table resume
(
    uuid      char(36) primary key not null,
    full_name text                 not null
);

create table contact
(
    id          serial,
    resume_uuid char(36) not null constraint contact_resume_uuid_fk references resume on delete cascade,
    type        text     not null,
    value       text     not null
);
create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table section
(
    type        text,
    value       text,
    resume_uuid text not null constraint section_resume_uuid_fk references resume on delete cascade
);