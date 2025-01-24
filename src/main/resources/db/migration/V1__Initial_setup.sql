CREATE TABLE customer (
    customer_id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE task (
    task_id UUID PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    customer_id UUID NOT NULL,
    CONSTRAINT customer_initiates_task_fk FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);