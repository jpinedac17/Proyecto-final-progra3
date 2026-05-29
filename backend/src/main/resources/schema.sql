DROP TABLE IF EXISTS nodes CASCADE;

CREATE TABLE nodes (
    id BIGSERIAL PRIMARY KEY,
    value VARCHAR(255) NOT NULL,
    parent_id BIGINT NULL,
    CONSTRAINT fk_nodes_parent
        FOREIGN KEY (parent_id)
        REFERENCES nodes(id)
        ON DELETE CASCADE
);