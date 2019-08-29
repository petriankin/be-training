create table if not exists public.dog (id UUID,
name varchar(100) not null,
date_of_birth date,
height int not null,
weight int not null);