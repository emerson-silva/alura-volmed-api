ALTER TABLE pacientes ADD ativo TINYINT(1);

UPDATE pacientes SET ativo = 1;

ALTER TABLE pacientes ALTER COLUMN ativo SET DEFAULT 1;
