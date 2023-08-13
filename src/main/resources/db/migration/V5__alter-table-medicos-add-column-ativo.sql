ALTER TABLE medicos ADD ativo TINYINT(1);

UPDATE medicos SET ativo = 1;

ALTER TABLE medicos ALTER COLUMN ativo SET DEFAULT 1;
