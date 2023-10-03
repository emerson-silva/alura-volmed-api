ALTER TABLE usuarios ADD enabled TINYINT(1);

UPDATE usuarios SET enabled = 1;

ALTER TABLE usuarios ALTER COLUMN enabled SET DEFAULT 1;
