-- $Revision$
-- $Date$

ALTER TABLE ofMessageArchive ADD COLUMN messageID BIGINT NULL;
ALTER TABLE ofMessageArchive ADD COLUMN stanza TEXT NULL;

ALTER TABLE ofMessageArchive ADD COLUMN id VARCHAR(255) NULL;
ALTER TABLE ofMessageArchive ADD COLUMN subject VARCHAR(255) NULL;
ALTER TABLE ofMessageArchive ADD COLUMN messageType VARCHAR(255) NOT NULL default 'txt';
ALTER TABLE ofMessageArchive ADD COLUMN targetType TINYINT NOT NULL default 1;
ALTER TABLE ofMessageArchive ADD COLUMN atUsers TEXT NULL;
ALTER TABLE ofMessageArchive ADD COLUMN lng VARCHAR(255) NULL;
ALTER TABLE ofMessageArchive ADD COLUMN lat VARCHAR(255) NULL;


-- Update database version
UPDATE ofVersion SET version = 3 WHERE name = 'monitoring';