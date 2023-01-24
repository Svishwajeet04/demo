
create table if not exists users(
  name character varying,
  id integer generated always as identity primary key not null,
  email character varying,
  password character varying
  );

create table if not exists task(
id integer generated always as identity primary key not null,
name character varying,
description character varying,
deadline timestamp ,
user_id integer references users(id),
frequency character varying
);

create table if not exists task_instance(
id integer generated always as identity primary key not null,
start_time timestamp ,
end_time timestamp ,
status character varying,
task_id integer references task(id),
message character varying
);