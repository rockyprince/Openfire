TRUNCATE TABLE ofAPNS;
INSERT INTO ofAPNS
(username, devicetoken)
SELECT username, devicetoken
FROM ofAPNS;

