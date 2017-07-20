Following is the design of news feed agggrator
1. Read news feed as RSS and save feed details in mysqldb
2. Scrap and save pages in mongo db using the url from news feed from Step#1
3. While filtering the news for sentiment - do mongo db text search, which is faster and works well with matching the text
4. Once the news is tagged with sentiment - save the news details in mysql db
5. As we would have the whole page saved in mongodb- we would have one more advantage of never loosing the news even if the link is removed from original website after long time.. for some reason..

Sample Feed source
INSERT INTO `folitics`.`feedsource`
(`description`,`disabled`,`edited`,`feedURL`,`name`,`records`,`scheduleCron`,`timestamp`)
VALUES('First Post News RSS Feed',0,CURRENT_TIMESTAMP,'http://www.firstpost.com/politics/feed','FirstPost','50','0 0 0/2 1/1 * ? *',CURRENT_TIMESTAMP);

INSERT INTO `folitics`.`feedsource`
(`description`,`disabled`,`edited`,`feedURL`,`name`,`records`,`scheduleCron`,`timestamp`)
VALUES('NDTV News RSS Feed',0,CURRENT_TIMESTAMP,'http://feeds.feedburner.com/NdtvNews-TopStories','NDTV','50','0 0 0/2 1/1 * ? *',CURRENT_TIMESTAMP);

INSERT INTO `folitics`.`feedsource`
(`description`,`disabled`,`edited`,`feedURL`,`name`,`records`,`scheduleCron`,`timestamp`)
VALUES('Reuters News RSS Feed',0,CURRENT_TIMESTAMP,'http://feeds.reuters.com/reuters/INtopNews','Reuters','50','0 0 0/2 1/1 * ? *',CURRENT_TIMESTAMP);

INSERT INTO `folitics`.`feedsource`
(`description`,`disabled`,`edited`,`feedURL`,`name`,`records`,`scheduleCron`,`timestamp`)
VALUES('IBN Live News RSS Feed',0,CURRENT_TIMESTAMP,'http://www.ibnlive.com/rss/politics.xml','IBNLive','50','0 0 0/2 1/1 * ? *',CURRENT_TIMESTAMP);

INSERT INTO `folitics`.`feedsource`
(`description`,`disabled`,`edited`,`feedURL`,`name`,`records`,`scheduleCron`,`timestamp`)
VALUES('ANI Politics News RSS Feed',0,CURRENT_TIMESTAMP,'http://www.aninews.in/rssfeed/11-politics.html','ANI','50','0 0 0/2 1/1 * ? *',CURRENT_TIMESTAMP);

Header 
Content-Type:application/json

API to add feed source with default settings
http://localhost:8080/rssfeed/addsource?sourceName=<name>&sourceURL=<source url>
Example
Mehthod:POST
sourceName=ANI
sourceURL = http://www.aninews.in/rssfeed/11-politics.html
Final URL: http://localhost:8080/rssfeed/addsource?sourceName=ANI&sourceURL=http://www.aninews.in/rssfeed/11-politics.html

API to fetch all resource
http://localhost:8080/rssfeed/getallsources
Mehthod:GET
API to run feed load from source
http://localhost:8080/rssfeed/loadfeed?sourceName=<FeedSource.name>
Mehthod:POST
sourceName=NDTV
sourceName=FirstPost
sourceName=Reuters
sourceName=IBNLive
Final URL: http://localhost:8080/rssfeed/loadfeed?sourceName=NDTV

API to filter news on keyowrd
Mehthod:POST
http://localhost:8080/rssfeed/search?keyword=universities

API to disable feed source (get the source id from getallfeedsources api)
Mehthod:POST
http://localhost:8080/rssfeed/disablesource?id=2

