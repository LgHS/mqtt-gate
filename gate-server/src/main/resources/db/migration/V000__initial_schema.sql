create table groups (
    id uuid primary key default uuid_generate_v4(),
    parent_id uuid references groups(id),
    name text unique not null
);
grant select, insert, update, delete on table groups to lghs_gate_server_app;


create table devices (
    id uuid primary key default uuid_generate_v4(),
    name text unique not null,
    offline bool not null default false,
    type text not null -- gate, tool,
);
grant select, insert, update, delete on table devices to lghs_gate_server_app;


create table device_groups (
    device_id uuid not null references devices(id),
    group_id uuid not null references groups(id),
    primary key (device_id, group_id)
);
grant select, insert, update, delete on table device_groups to lghs_gate_server_app;


create type user_role as enum ('ROLE_MEMBER', 'ROLE_ADMIN');
create table users (
    id integer primary key,
    uuid uuid unique not null,
    name text not null,
    username text unique not null,
    email text unique not null,
    role user_role not null default 'ROLE_MEMBER'::user_role
);
grant select, insert, update, delete on table users to lghs_gate_server_app;


create table user_groups (
    user_id uuid not null references users(uuid),
    group_id uuid not null references groups(id),
    primary key (user_id, group_id)
);
grant select, insert, update, delete on table user_groups to lghs_gate_server_app;


create type decision as enum ('ALLOWED', 'DENIED');
create table access_rules (
    id uuid primary key,
    user_id uuid references users(uuid),
    group_id uuid references groups(id),
    device_id uuid not null references devices(id),
    start_time time not null,
    end_time time not null,
    weekdays int[] not null,
    decision decision not null,
    validity_start date not null,
    validity_end date not null,
    check ((user_id is null) <> (group_id is null))
);
grant select, insert, update, delete on table access_rules to lghs_gate_server_app;


create table cards (
    id uuid primary key default uuid_generate_v4(),
    uid int unique not null,
    name text,
    token uuid unique,
    last_renew timestamp,
    last_renew_duration interval,
    user_id uuid not null references users(uuid),
    check ((last_renew is null) = (last_renew_duration is null))
);
grant select, insert, update, delete on table cards to lghs_gate_server_app;


-- TODO audit logs for openings,

-- select
--   decision
-- from access_rules
-- inner join groups on groups.id = users.group_id
-- where (user_id = :user_id or user_id is null)
--   and device_id = :device_id
--   and start_time <= now()
--   and end_time > now()
--   and extract(DOW from now()) @> weekdays
-- order by user_id nulls last, decision desc -- (DENIED FIRSTS)
-- limit 1
