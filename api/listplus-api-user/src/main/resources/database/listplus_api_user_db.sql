CREATE TABLE IF NOT EXISTS lp_user_role (
    role_id TINYINT(1) PRIMARY KEY NOT NULL,
    role_name VARCHAR(25) NOT NULL,
    role_active TINYINT(1) NOT NULL DEFAULT(1)
);

CREATE TABLE IF NOT EXISTS lp_user (
    user_id VARCHAR(40) PRIMARY KEY NOT NULL DEFAULT(UUID()),
    user_name VARCHAR(50) NOT NULL,
    user_email VARCHAR(50) NOT NULL,
    user_password VARCHAR(50) NOT NULL,
    user_role TINYINT(1) NOT NULL,
    user_created_on TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP()),
    user_last_updated_on TIMESTAMP NOT NULL DEFAULT(CURRENT_TIMESTAMP()),
    user_active TINYINT(1) NOT NULL DEFAULT(1),
    CONSTRAINT user_role_fk FOREIGN KEY (user_role) REFERENCES lp_user_role (role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE UNIQUE INDEX user_email_index ON lp_user (user_email);
CREATE UNIQUE INDEX user_name_index ON lp_user (user_name);

INSERT INTO lp_user_role VALUES (0, "User", 1);
INSERT INTO lp_user_role VALUES (1, "Moderator", 1);
INSERT INTO lp_user_role VALUES (2, "Admin", 1);

INSERT INTO lp_user VALUES (UUID(), "listplusadmin", "admin@listplus.cl", "admin12345", 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 1);

DELIMITER //

CREATE TRIGGER lp_user_last_update
AFTER UPDATE ON lp_user
FOR EACH ROW
BEGIN
	UPDATE lp_user SET user_last_updated_on = CURRENT_TIMESTAMP() WHERE OLD.user_id = NEW.user_id;
END;//

DELIMITER ;