/*
-- Query: select * from notificationlookup
LIMIT 0, 1000

-- Date: 2016-08-22 20:10
*/
INSERT INTO `notificationlookup` (`id`,`action`,`componentType`,`notificationTo`) VALUES (1,'follow','sentiment','followers');
INSERT INTO `notificationlookup` (`id`,`action`,`componentType`,`notificationTo`) VALUES (2,'like','sentiment','Likers');
INSERT INTO `notificationlookup` (`id`,`action`,`componentType`,`notificationTo`) VALUES (3,'dislike','sentiment','dislikers');
