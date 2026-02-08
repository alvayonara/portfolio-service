CREATE TABLE IF NOT EXISTS projects (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        title VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    tech_stack VARCHAR(255) DEFAULT NULL,
    repo_url VARCHAR(255) DEFAULT NULL,
    s3_key VARCHAR(255) DEFAULT NULL,
    published BOOLEAN DEFAULT FALSE,
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

CREATE TABLE IF NOT EXISTS profile (
                                       id BIGINT PRIMARY KEY,

                                       full_name    VARCHAR(100) NOT NULL,
    headline     VARCHAR(150),
    summary      TEXT,
    location     VARCHAR(100),
    email        VARCHAR(150),

    github_url   VARCHAR(255),
    linkedin_url VARCHAR(255),
    s3_key   VARCHAR(255) DEFAULT NULL,

    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
    );

-- INSERT INTO profile (
--     id,
--     full_name,
--     headline,
--     summary,
--     location,
--     email
-- ) VALUES (
--              1,
--              'Alva Yonara',
--              'Backend Engineer',
--              'xxx',
--              'Indonesia',
--              'alvayonara@gmail.com'
--          );

CREATE TABLE IF NOT EXISTS experience (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            company VARCHAR(100) NOT NULL,
                            title VARCHAR(100) NOT NULL,
                            location VARCHAR(100),
                            description TEXT,
                            start_date DATE NOT NULL,
                            end_date DATE,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS skill_group (
                                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                           name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS skill (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     skill_group_id BIGINT NOT NULL,
                                     name VARCHAR(100) NOT NULL,
    level VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_skill_group
    FOREIGN KEY (skill_group_id)
    REFERENCES skill_group(id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS education (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           institution_name VARCHAR(150) NOT NULL,
                           degree VARCHAR(100) NOT NULL,
                           field_of_study VARCHAR(120),
                           start_year INT NOT NULL,
                           end_year INT,
                           description TEXT,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS resume (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        s3_key VARCHAR(255) NOT NULL,
                        original_filename VARCHAR(255) NOT NULL,
                        content_type VARCHAR(100),
                        size BIGINT,
                        uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);