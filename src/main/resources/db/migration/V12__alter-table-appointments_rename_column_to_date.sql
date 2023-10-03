alter table appointments drop column data;

alter table appointments add date datetime not null;
