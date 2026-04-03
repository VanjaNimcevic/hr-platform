CREATE DATABASE IF NOT EXISTS hr_platform;
USE hr_platform;

CREATE TABLE candidate (
                           id        BIGINT AUTO_INCREMENT PRIMARY KEY,
                           full_name VARCHAR(100) NOT NULL,
                           dob       DATE         NOT NULL,
                           phone     VARCHAR(20),
                           email     VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE skill (
                       id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                       skill_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE candidate_skill (
                                 candidate_id BIGINT NOT NULL,
                                 skill_id     BIGINT NOT NULL,
                                 PRIMARY KEY (candidate_id, skill_id),
                                 FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE,
                                 FOREIGN KEY (skill_id)     REFERENCES skill(id)     ON DELETE CASCADE
);