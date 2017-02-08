# $Revision$
# $Date$

INSERT INTO ofVersion (name, version) VALUES ('apns', 1);

CREATE TABLE ofAPNS (
	username VARCHAR(191) NOT NULL,
	devicetoken CHAR(64) NOT NULL,
  PRIMARY KEY (username)
);