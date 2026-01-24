CREATE TABLE IF NOT EXISTS projects (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        title VARCHAR(255) NOT NULL,
    description TEXT,
    tech_stack VARCHAR(255),
    repo_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_roles (
                            user_id BIGINT NOT NULL,
                            role VARCHAR(30) NOT NULL,
                            FOREIGN KEY (user_id) REFERENCES users(id)
);
-- INSERT INTO users (username, password, enabled)
-- VALUES ('admin', 'change to bcrypt pass', true);

-- INSERT INTO user_roles (user_id, role)
-- VALUES (1, 'ADMIN');