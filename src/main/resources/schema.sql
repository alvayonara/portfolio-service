CREATE TABLE IF NOT EXISTS projects (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        title VARCHAR(255) NOT NULL,
    description TEXT,
    tech_stack VARCHAR(255),
    repo_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
    );