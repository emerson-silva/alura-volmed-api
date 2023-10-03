ALTER TABLE usuarios ADD role varchar(20);

UPDATE usuarios SET role = "ROLE_USER";

ALTER TABLE usuarios ALTER COLUMN role SET DEFAULT "ROLE_USER";
