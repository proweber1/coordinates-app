-- install extension for compute geo points
create extension earthdistance cascade;

create table shops
(
  id serial not null
    constraint shops_pkey
    primary key,
  name varchar(300) not null,
  lng double precision not null
    constraint shops_lng_check
    check ((lng >= ('-90'::integer)::double precision) AND (lng <= (90)::double precision)),
  lat double precision not null
    constraint shops_lat_check
    check ((lat >= ('-180'::integer)::double precision) AND (lat <= (180)::double precision)),
  open_time time,
  close_time time
)
;

create table search_requests_log
(
  id serial not null
    constraint search_requests_log_pkey
    primary key,
  user_name varchar(300) not null,
  lat double precision not null
    constraint search_requests_log_lat_check
    check ((lat >= ('-180'::integer)::double precision) AND (lat <= (180)::double precision)),
  lng double precision not null
    constraint search_requests_log_lng_check
    check ((lng >= ('-90'::integer)::double precision) AND (lng <= (90)::double precision)),
  search_datetime timestamp default now()
)
;

create table search_requests_founded_shops
(
  shop_id bigint not null
    constraint search_requests_founded_shops_shops_id_fk
    references shops
    on update cascade on delete cascade,
  search_request_id bigint not null
    constraint search_requests_founded_shops_search_requests_log_id_fk
    references search_requests_log
    on update cascade on delete cascade,
  constraint search_requests_founded_shops_shop_id_search_request_id_pk
  primary key (shop_id, search_request_id)
)
;
