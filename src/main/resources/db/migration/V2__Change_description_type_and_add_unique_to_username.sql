ALTER TABLE task
ALTER COLUMN description TYPE VARCHAR(280);

ALTER TABLE customer
ADD CONSTRAINT unique_username UNIQUE (username)
ALTER COLUMN password TYPE VARCHAR(60);