INSERT INTO `userRole` (`id`,`userRole`) VALUES (1,'ADMIN');
INSERT INTO `userRole` (`id`,`userRole`) VALUES (2,'USER');

INSERT INTO `user` (`id`,`badge`,`dob`,`emailId`,`gender`,`inclinationAggregation`,`maritalStatus`,`name`,`password`,`points`,`religion`,`state`,`status`,`username`,`role_id`) VALUES (1,'Badge','1992-04-30 00:00:00','vanditapandey@gmail.com','Male',1,'Married','vandita','12345',50000,'Hindu','Active','VerifiedByEmail','vandita',1);
INSERT INTO `user` (`id`,`badge`,`dob`,`emailId`,`gender`,`inclinationAggregation`,`maritalStatus`,`name`,`password`,`points`,`religion`,`state`,`status`,`username`,`role_id`) VALUES (2,'Badge','1992-04-30 00:00:00','vanditapandey@gmail.com','Male',1,'Married','harish','123',100,'Hindu','Active','VerifiedByEmail','harish',2);
INSERT INTO `user` (`id`,`badge`,`dob`,`emailId`,`gender`,`inclinationAggregation`,`maritalStatus`,`name`,`password`,`points`,`religion`,`state`,`status`,`username`,`role_id`) VALUES (3,'badge','1992-04-30 00:00:00','vanditapandey@gmail.com','Male',1,'Married','name','123',100,'hindu','Active','VerifiedByEmail','username',1);
INSERT INTO `user` (`id`,`badge`,`dob`,`emailId`,`gender`,`inclinationAggregation`,`maritalStatus`,`name`,`password`,`points`,`religion`,`state`,`status`,`username`,`role_id`) VALUES (4,'Badge','1992-04-30 00:00:00','vanditapandey@gmail.com','Male',1,'Married','harish','123',100,'Hindu','Active','VerifiedByEmail','bagora',2);

INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (1,'2012-12-12 00:00:00',1,'Track Promise to Nation','2012-12-12 00:00:00',1,'Track Promise to Nation','Category','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (2,'2012-12-12 00:00:00',1,'Track Promise to Business','2012-12-12 00:00:00',1,'Track Promise to Business','Category','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (3,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Track Promise to People','Category','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (4,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Price Rise','SubCategory','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (5,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Economy','SubCategory','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (6,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Inflation','Indicator','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (7,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'WPI','Indicator','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (8,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Average Income','Indicator','Active');
INSERT INTO `category` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`name`,`type`,`status`) VALUES (9,'2012-12-12 00:00:00',1,'Track Promise to People','2012-12-12 00:00:00',1,'Interest Rates','Indicator','Active');


INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (4,1);
INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (5,1);
INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (6,4);
INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (7,4);
INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (8,4);
INSERT INTO `categoryHierarchy` (`childId`,`parentId`) VALUES (9,5);


INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (1,'GPIChartService','GPI','GPIChar');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (2,'GAChartService','Inflation','Inflation');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (3,'GAChartService','WPI','WPI');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (4,'GAChartService','GDP','GDP');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (5,'GAChartService','InterestRate','InterestRate');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (6,'verdictChartService','verdictSexService','Local verdict by sex');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (7,'VerdictChartService','verdictReligionService','Local verdict by religon');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (8,'VerdictChartService','verdictRegionService','Local verdict by region');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (9,'VerdictChartService','verdictQualificationService','Local verdict by qualification');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (10,'VerdictChartService','verdictMaritalStatusService','Local verdict by maritalstatus');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (11,'VerdictChartService','verdictAgeGroupService','Local verdict by age group');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (12,'VerdictChartService','verdictOverAllService','OverAll verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (14,'globalVerdictChartService','globalVerdictAgeGroup','Global Sex verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (15,'globalVerdictChartService','globalOverAllVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (16,'globalVerdictChartService','globalSexVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (17,'globalVerdictChartService','globalMaritalStatusVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (18,'globalVerdictChartService','globalRegionVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (19,'globalVerdictChartService','globalReligionVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (20,'globalVerdictChartService','globalQualificationVerdictService','Global  verdict');
INSERT INTO `chart` (`id`,`chartID`,`chartSecondaryID`,`description`) VALUES (21,'globalVerdictChartService','globalVerdictPromiseIndicator','Global promise indicator');



INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (1,'yaxislabel','Percentage',6);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (2,'xaxislabel','Gender',6);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (3,'title','VerdictSex',6);

INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (5,'yaxislabel','Score',1);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (6,'xaxislabel','DD',1);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (7,'title','GPI',1);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (8,'yaxislabel','Score',2);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (9,'xaxislabel','DD',2);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (10,'title','Inflation',2);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (11,'yaxislabel','Score',3);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (12,'xaxislabel','DD',3);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (13,'title','WPI',3);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (14,'yaxislabel','Score',4);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (15,'xaxislabel','DD',4);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (16,'title','GDP',4);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (17,'yaxislabel','Score',5);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (18,'xaxislabel','DD',5);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (19,'title','InterestRate',5);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (20,'yaxislabel','Percentage',7);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (21,'xaxislabel','Gender',7);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (22,'title','Religion Verdict',7);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (23,'yaxislabel','Percentage',8);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (24,'xaxislabel','Gender',8);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (25,'title','Region Verdict',8);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (26,'yaxislabel','Percentage',9);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (27,'xaxislabel','Gender',9);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (28,'title','Qualification Verdict',9);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (29,'yaxislabel','Percentage',10);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (30,'xaxislabel','Gender',10);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (31,'title','Marital Status Verdict',10);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (32,'yaxislabel','Percentage',11);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (33,'xaxislabel','Gender',11);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (34,'title','Age Verdict',11);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (35,'yaxislabel','Percentage',12);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (36,'xaxislabel','Gender',12);
INSERT INTO `chartMetadata` (`id`,`propertyName`,`propertyValue`,`chartid`) VALUES (37,'title','Age Verdict',12);

INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (1,'2012-12-12 00:00:00',0,-400,'2012-12-12 00:00:00','2010-01-01 00:00:00','4.0-6.0',1,5,'1','Best Performance','2010-01-01 00:00:00',1400,1000,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (2,'2012-12-12 00:00:00',0,-200,'2012-12-12 00:00:00','2010-05-01 00:00:00','6.0-8.0',1,6,'1','On Track','2010-05-01 00:00:00',1400,1200,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (3,'2012-12-12 00:00:00',0,0,'2012-12-12 00:00:00','2010-09-01 00:00:00','6.0-8.0',1,7,'1','On Track','2010-09-01 00:00:00',1400,1400,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (4,'2012-12-12 00:00:00',0,200,'2012-12-12 00:00:00','2011-01-01 00:00:00','8.0-10.0',1,8,'1','UnSatisfactory','2011-01-01 00:00:00',1400,1600,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (5,'2012-12-12 00:00:00',0,-50,'2012-12-12 00:00:00','2011-05-01 00:00:00','3.0-4.0',1,3,'1','On Track','2011-05-01 00:00:00',350,300,7);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (6,'2012-12-12 00:00:00',0,50,'2012-12-12 00:00:00','2011-09-01 00:00:00','4.0-5.0',1,4,'1','Best Performance','2011-09-01 00:00:00',350,400,7);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (7,'2012-12-12 00:00:00',0,-150,'2012-12-12 00:00:00','2012-01-01 00:00:00','2.0-3.0',1,2,'1','UnSatisfactory','2012-01-01 00:00:00',350,200,7);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (8,'2012-12-12 00:00:00',0,-250,'2012-12-12 00:00:00','2012-05-01 00:00:00','1.0-2.0',1,1,'1','Fooling US','2012-05-01 00:00:00',350,100,7);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (9,'2012-12-12 00:00:00',0,6,'2012-12-12 00:00:00','2012-09-01 00:00:00','2.0-6.0',1,5,'1','UnSatisfactory','2012-09-01 00:00:00',21,15,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (10,'2012-12-12 00:00:00',0,3,'2012-12-12 00:00:00','2013-01-01 00:00:00','6.0-8.0',1,6,'1','On Track','2013-01-01 00:00:00',21,18,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (11,'2012-12-12 00:00:00',0,0,'2012-12-12 00:00:00','2013-05-01 00:00:00','6.0-8.0',1,7,'1','On Track','2013-05-01 00:00:00',21,21,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (12,'2012-12-12 00:00:00',0,9,'2012-12-12 00:00:00','2013-09-01 00:00:00','2.0-6.0',1,4,'1','UnSatisfactory','2013-09-01 00:00:00',21,12,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (13,'2012-12-12 00:00:00',0,90,'2012-12-12 00:00:00','2014-01-01 00:00:00','2.0-6.0',1,4,'1','UnSatisfactory','2014-01-01 00:00:00',210,120,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (14,'2012-12-12 00:00:00',0,60,'2012-12-12 00:00:00','2014-05-01 00:00:00','2.0-6.0',1,5,'1','UnSatisfactory','2014-05-01 00:00:00',210,150,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (15,'2012-12-12 00:00:00',0,30,'2012-12-12 00:00:00','2014-09-01 00:00:00','6.0-8.0',1,6,'1','On Track','2014-09-01 00:00:00',210,180,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (16,'2012-12-12 00:00:00',0,0,'2012-12-12 00:00:00','2015-01-01 00:00:00','6.0-8.0',1,7,'1','On Track','2015-01-01 00:00:00',210,210,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (17,'2012-12-12 00:00:00',0,400,'2012-12-12 00:00:00','2015-05-01 00:00:00','8.0-10.0',1,9,'1','UnSatisfactory','2012-12-12 00:00:00',1400,1800,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (18,'2012-12-12 00:00:00',0,-600,'2012-12-12 00:00:00','2015-09-01 00:00:00','4.0-6.0',1,4,'1','Best Performance','2012-12-12 00:00:00',1400,800,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (20,'2012-12-12 00:00:00',0,600,'2012-12-12 00:00:00','2016-01-01 00:00:00','10.0-12.0',1,10,'1','Fooling US','2012-12-12 00:00:00',1400,2000,6);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (21,'2012-12-12 00:00:00',0,12,'2012-12-12 00:00:00','2016-05-01 00:00:00','2.0-6.0',1,3,'1','UnSatisfactory','2012-12-12 00:00:00',21,9,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (22,'2012-12-12 00:00:00',0,15,'2012-12-12 00:00:00','2016-09-01 00:00:00','2.0-6.0',1,2,'1','UnSatisfactory','2012-12-12 00:00:00',21,6,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (23,'2012-12-12 00:00:00',0,18,'2012-12-12 00:00:00','2017-01-01 00:00:00','1.0-2.0',1,1,'1','Fooling US','2012-12-12 00:00:00',21,3,8);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (24,'2012-12-12 00:00:00',0,120,'2012-12-12 00:00:00','2017-05-01 00:00:00','2.0-6.0',1,3,'1','UnSatisfactory','2012-12-12 00:00:00',210,90,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (25,'2012-12-12 00:00:00',0,180,'2012-12-12 00:00:00','2017-09-01 00:00:00','1.0-2.0',1,1,'1','Fooling US','2012-12-12 00:00:00',210,30,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (26,'2012-12-12 00:00:00',0,150,'2012-12-12 00:00:00','2018-01-01 00:00:00','2.0-6.0',1,2,'1','UnSatisfactory','2012-12-12 00:00:00',210,60,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (27,'2012-12-12 00:00:00',0,-30,'2012-12-12 00:00:00','2018-05-01 00:00:00','8.0-10.0',1,8,'1','Best Performance','2012-12-12 00:00:00',210,240,9);
INSERT INTO `IndicatorData` (`id`,`createTime`,`deleted`,`delta`,`editTime`,`effectfromdate`,`idealValueRange`,`indicatorvalue`,`score`,`state`,`thresholdcategory`,`updateddate`,`weightedIdealValue`,`weightedValue`,`indicatorId`) VALUES (28,'2012-12-12 00:00:00',0,-60,'2012-12-12 00:00:00','2018-09-01 00:00:00','8.0-10.0',1,9,'1','Best Performance','2012-12-12 00:00:00',210,270,9);


INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (1,'2012-12-12 00:00:00',1,'2012-12-12 00:00:00','New','Fooling US',12,10,6);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (2,'2012-12-12 00:00:00',2,'2012-12-12 00:00:00','New','UnSatisfactory',10,8,6);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (3,'2012-12-12 00:00:00',3,'2012-12-12 00:00:00','New','On Track',8,6,6);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (4,'2012-12-12 00:00:00',4,'2012-12-12 00:00:00','New','Best Performance',6,4,6);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (5,'2012-12-12 00:00:00',1,'2012-12-12 00:00:00','New','Fooling US',2,1,7);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (6,'2012-12-12 00:00:00',2,'2012-12-12 00:00:00','New','UnSatisfactory',3,2,7);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (7,'2012-12-12 00:00:00',3,'2012-12-12 00:00:00','New','On Track',4,3,7);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (8,'2012-12-12 00:00:00',4,'2012-12-12 00:00:00','New','Best Performance',5,4,7);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (9,'2012-12-12 00:00:00',1,'2012-12-12 00:00:00','New','Fooling US',2,1,8);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (10,'2012-12-12 00:00:00',2,'2012-12-12 00:00:00','New','UnSatisfactory',6,2,8);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (11,'2012-12-12 00:00:00',3,'2012-12-12 00:00:00','New','On Track',8,6,8);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (12,'2012-12-12 00:00:00',4,'2012-12-12 00:00:00','New','Best Performance',10,8,9);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (13,'2012-12-12 00:00:00',1,'2012-12-12 00:00:00','New','Fooling US',2,1,9);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (14,'2012-12-12 00:00:00',2,'2012-12-12 00:00:00','New','UnSatisfactory',6,2,9);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (15,'2012-12-12 00:00:00',3,'2012-12-12 00:00:00','New','On Track',8,6,9);
INSERT INTO `indicatorthreshold` (`id`,`createTime`,`direction`,`editTime`,`state`,`threshHoldCategory`,`threshold_end`,`threshold_start`,`indicatorId`) VALUES (16,'2012-12-12 00:00:00',4,'2012-12-12 00:00:00','New','Best Performance',10,8,9);


INSERT INTO `IndicatorWeightedData` (`id`,`createTime`,`deleted`,`editTime`,`effectivefromdate`,`impactOnChart`,`state`,`updateddate`,`weightage`,`indicatorId`) VALUES (1,'2012-12-12 00:00:00',0,'2012-12-12 00:00:00','2012-12-12 00:00:00',1,'New','2012-12-12 00:00:00',200,6);
INSERT INTO `IndicatorWeightedData` (`id`,`createTime`,`deleted`,`editTime`,`effectivefromdate`,`impactOnChart`,`state`,`updateddate`,`weightage`,`indicatorId`) VALUES (2,'2012-12-12 00:00:00',0,'2012-12-12 00:00:00','2012-12-12 00:00:00',1,'New','2012-12-12 00:00:00',100,7);
INSERT INTO `IndicatorWeightedData` (`id`,`createTime`,`deleted`,`editTime`,`effectivefromdate`,`impactOnChart`,`state`,`updateddate`,`weightage`,`indicatorId`) VALUES (3,'2012-12-12 00:00:00',0,'2012-12-12 00:00:00','2012-12-12 00:00:00',-1,'New','2012-12-12 00:00:00',3,8);
INSERT INTO `IndicatorWeightedData` (`id`,`createTime`,`deleted`,`editTime`,`effectivefromdate`,`impactOnChart`,`state`,`updateddate`,`weightage`,`indicatorId`) VALUES (4,'2012-12-12 00:00:00',0,'2012-12-12 00:00:00','2012-12-12 00:00:00',-1,'New','2012-12-12 00:00:00',30,9);



INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-03-21 15:22:10','Active',30);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-03-15 15:22:10','Active',40);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-03-10 15:22:10','Active',70);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-03-5 15:22:10','Active',10);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-03-1 15:22:10','Active',80);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-25 15:22:10','Active',20);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-20 15:22:10','Active',40);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-15 15:22:10','Active',10);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-10 15:22:10','Active',50);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-5 15:22:10','Active',30);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-02-1 15:22:10','Active',20);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-01-25 15:22:10','Active',50);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-01-20 15:22:10','Active',90);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-01-15 15:22:10','Active',70);
INSERT INTO `gpipoint` (`createTime`,`editTime`,`endTime`,`eventReportPoints`,`indicatorChangePoints`,`opinionResponseAggregationPoints`,`startTime`,`state`,`totalPoints`) VALUES ('2016-03-21 15:22:10','2016-03-21 15:22:10','2016-03-21 15:22:10',10,10,10,'2016-01-10 15:22:10','Active',60);



INSERT INTO `milestone` (`gpiid`,`milestone`,`url`,`description`,`milestone_type`) VALUES (7,10,'https://www.google.co.in','Petrol price decrease by 3 rs.','Mini');
INSERT INTO `milestone` (`gpiid`,`milestone`,`url`,`description`,`milestone_type`) VALUES (12,10,'http://timesofindia.indiatimes.com/india/UP-Punjab-polls-may-end-master-strategist-Prashant-Kishors-glorious-run/articleshow/51300483.cms','UP, Punjab polls may end \'master strategist\'','mini');
INSERT INTO `milestone` (`gpiid`,`milestone`,`url`,`description`,`milestone_type`) VALUES (10,20,'https://github.com','jnu issue','Mega');
INSERT INTO `milestone` (`gpiid`,`milestone`,`url`,`description`,`milestone_type`) VALUES (2,10,'http://www.msn.com/en-in/money/taxes/why-arun-jaitely%E2%80%99s-epf-tax-rollback-is-a-big-relief-for-india%E2%80%99s-salaried-class/ar-AAgvNaA?li=AAggbRN','Arun Jaitely’s EPF tax rollback','mini');
INSERT INTO `milestone` (`gpiid`,`milestone`,`url`,`description`,`milestone_type`) VALUES (5,20,'http://timesofindia.indiatimes.com/world/pakistan/Pak-court-summons-Musharraf-in-treason-case/articleshow/51309415.cms','Pak court summons Musharraf in treason case','Mega');



INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (1,'govtSchemeData','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (2,'Bumper','Contest');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (3,'Mini','Contest');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (4,'Mega','Contest');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (5,'Image','GPI');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (6,'Video','GPI');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (7,'Link','GPI');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (8,'Graph','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (9,'LocalIndicatorGraph','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (10,'GlobalIndicatorGraph','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (11,'ComparisonGraph','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (12,'SubmitFact','GA');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (13,'Opinion','GPI');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (14,'LocalVerdict','Verdict');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (15,'LocalVerdictReport','Verdict');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (16,'GlobalVerdictReport','Verdict');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (17,'Sentiment','GPI');
INSERT INTO `module` (`id`,`componentType`,`module`) VALUES (18,'Task','Task');


INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (1,'Consumer Affairs','Consumer cooperative policy formulating & price monitoring body');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (2,'Public Works Department','Responsible for construction & maintenance of capital assets of Government');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (3,'Human Resources Development ','Promotion & formulation of education policies');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (4,'Electronic Governance ','Home page of electronic governance');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (5,'Department of Telecommunications',' Home page of Department of Telecommunications and Telecom services');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (6,'Department of Statistics','Development of statistical system');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (7,'India Post ','National postal services organization');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (8,'Indian Meteorological Department ','Weather updates and earthquake reports');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (9,'Department of Adminstrative Reforms &  Grievances','Nodal agency for administrative reforms');
INSERT INTO `department` (`id`,`departmentName`,`description`) VALUES (10,'Department of Revenue',' relating to all the Direct and Indirect Union Taxes ');


INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (1,'Bribe taken  in PWD department','Bribe');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (2,'Money of Pension can\'t be given to beneficiaries','Pension');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (3,'Crime rate can be increased due to poverty','Crime');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (4,'Due to reservation problem talented student can\'t get what they deserves','Admission');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (5,'big issue we are facing now','Illiteracy');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (6,' 92% people are in un organised sector.','Unemployment');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (7,'Terror is the biggest fear of people of india as they are always afraid of terrorist attaching the public places','Terrorism');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (8,'Hard to take action against this problem','Religious violence');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (9,'disaster in uttarakhand in India has put a strong reason of not omitting nature from the list.','Natural Disasters');
INSERT INTO `taskCategory1` (`id`,`description`,`name`) VALUES (10,'Problem of Inflation is increased day by day','Inflation');

INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (1,'P-104 J.V Complex,Race Course Road','jpeg','Indore','2016-07-25 00:00:00','2016-05-03 00:00:00','Corruption in India is a major issue that adversely affects its economy.','2016-06-10 00:00:00','nidhijain@gmail.com',b'0',b'0',b'0',22.00,17.00,'Pollution','Poverty',70000,'7047859871',b'0',b'0','Active','Corruption',1);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (2,'Arera Colony','jpeg','Bhopal','2016-07-25 00:00:00','2016-05-03 00:00:00','The income of more than 8 crore urban people is estimated to fall below poverty line','2016-06-10 00:00:00','ankushjain@yahoo.com',b'0',b'0',b'0',21.00,15.00,'Crime','Corruption',100000,'8104379688',b'0',b'0','Inactive','Pollution',2);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (3,'Rohini','jpeg','New,Delhi','2016-01-25 00:00:00','2016-01-03 00:00:00','The income of more than 8 crore urban people is estimated to fall below poverty line','2016-01-10 00:00:00','rajivsingh@gmail.com',b'0',b'0',b'0',29.00,11.00,'Health','Crime',71678,'7659753658',b'0',b'0','Disabled','Poverty',3);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (4,'Sector no. 57','png','Chandigarh','2016-07-25 19:13:59','2016-07-25 19:13:56','Terrorism mainly causes loss of lives, but also affects the country economically','2016-07-25 19:13:58','anujsharma@yahoo.com',b'0',b'0',b'0',34.00,16.00,'Tourism','Public greivances',100000,'9876123457',b'0',b'1','Created','Terrorism',1);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (5,'Nehru Nagar','png','Bihar','2016-07-25 19:15:51','2016-07-25 19:13:57','Bihar has 68.8% literacy rate,thats why the youth is misguided and involved in criminal as well as terror activities.','2016-07-25 19:13:55','ankitasharma@yahoo.com',b'0',b'0',b'0',13.00,12.00,'Terrorism','HRD ',34567,'9876123657',b'0',b'0','Created','Illiteracy',2);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (6,'Indira nagar','jpg','Allahabad','2016-07-26 11:58:19','2016-07-26 11:58:11',' Rising rates of food items and fuel have affected the pockets of the middle class so much, bringing them out on the roads in protest.','2016-07-26 11:58:17','mayurijain7@gmail.com',b'0',b'0',b'0',32.00,41.00,'Poverty','Consumer affairs',76123,'9856786523',b'0',b'0','Created','Inflation',1);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (7,'Sector no. 52','jpg','Jabalpur','2016-07-26 11:58:29','2016-07-26 12:06:04','Women in India live under a constant fear all the time. A fear of going out alone, disturbs every female mind living in India.','2016-07-26 12:06:09','shivanisingh@yahoo.com',b'0',b'0',b'0',15.00,19.00,'Crime','HRD',90000,'9634768923',b'0',b'0','Created','Violence against Women',2);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (8,'MR-09 Square','jpg','Mumbai','2016-07-26 13:02:19','2016-07-26 13:02:15','India is still dependent on foreign imports for transport equipments, machineries, iron and steel, paper, chemicals and fertilisers, plastic material etc.','2016-07-26 13:02:18','aksharashrivastava@gmail.com',b'0',b'0',b'0',12.00,32.00,'Inflation','Statics',67543,'7658901234',b'0',b'0','Created','Unbalanced Industrial Structure',1);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (9,'Street no.10','jpg','Noida','2016-07-26 13:12:09','2016-07-26 13:12:06','flood control and maintenance, old irrigation systems and conjunctive use of surface and ground water in the irrigation systems.','2016-07-26 13:12:08','jahidali@yahoo.com',b'0',b'0',b'0',18.00,9.00,'Poverty','Metrooligical',110000,'8712091589',b'0',b'0','Created','Water resources',2);
INSERT INTO `task` (`id`,`address`,`attachmentType`,`city`,`completionDate`,`creationDate`,`description`,`editDate`,`email`,`isDept`,`isLeader`,`isNGO`,`locationLat`,`locationLon`,`otherCategory`,`otherDepartment`,`peopleAffected`,`phone`,`showEmail`,`showMobileNo`,`status`,`subject`,`createdBy_id`) VALUES (10,'10 Downing Street','jpg','Hyderabad','2016-07-26 13:27:59','2016-07-26 13:27:54','Due to less income or to achieve certain objective leads to corruption in public life','2016-07-26 13:27:55','shyamshukla@gmail.com',b'0',b'0',b'0',20.00,18.00,'Corruption','Department of Revenue',90867,'9828506070',b'0',b'0','Created','Corruption in public life',1);


INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (1,2);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (2,2);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (3,3);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (4,3);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (5,3);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (6,8);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (7,9);
INSERT INTO `Task_department` (`taskId`,`departmentId`) VALUES (8,9);

INSERT INTO `peopleMet` (`id`,`actionTaken`,`departmentName`,`isHelpfull`,`location`,`pname`) VALUES (1,'None','Corruption',b'1','Indore','Nidhi Jain');
INSERT INTO `peopleMet` (`id`,`actionTaken`,`departmentName`,`isHelpfull`,`location`,`pname`) VALUES (2,'None','Pollution',b'1','bhopal','Ankush jain');
INSERT INTO `peopleMet` (`id`,`actionTaken`,`departmentName`,`isHelpfull`,`location`,`pname`) VALUES (3,'Active','Health',b'0','New Delhi','Rajiv Singh');
INSERT INTO `peopleMet` (`id`,`actionTaken`,`departmentName`,`isHelpfull`,`location`,`pname`) VALUES (4,'Created','public greviances',b'0','Chandigarh','ankita sharma');
INSERT INTO `peopleMet` (`id`,`actionTaken`,`departmentName`,`isHelpfull`,`location`,`pname`) VALUES (5,'Created','Dept. of Revenue',b'0','Mumbai','anuj sharma');

INSERT INTO `Task_person` (`taskId`,`PersonId`) VALUES (1,1);
INSERT INTO `Task_person` (`taskId`,`PersonId`) VALUES (2,2);


INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (1,1);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (2,1);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (3,3);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (4,6);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (5,7);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (6,7);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (7,9);
INSERT INTO `Task_category` (`taskId`,`categoryId`) VALUES (8,10);

INSERT INTO `component` (`id`,`componentType`) VALUES (1,'Sentiment');
INSERT INTO `component` (`id`,`componentType`) VALUES (2,'Sentiment');
INSERT INTO `component` (`id`,`componentType`) VALUES (3,'Opinion');
INSERT INTO `component` (`id`,`componentType`) VALUES (4,'Response');
INSERT INTO `component` (`id`,`componentType`) VALUES (5,'Link');
INSERT INTO `component` (`id`,`componentType`) VALUES (6,'GPI');
INSERT INTO `component` (`id`,`componentType`) VALUES (7,'GA');
INSERT INTO `component` (`id`,`componentType`) VALUES (8,'LocalVerdict');
INSERT INTO `component` (`id`,`componentType`) VALUES (9,'GlobalVerdict');
INSERT INTO `component` (`id`,`componentType`) VALUES (10,'Sentiment');
INSERT INTO `component` (`id`,`componentType`) VALUES (11,'Opinion');
INSERT INTO `component` (`id`,`componentType`) VALUES (12,'Response');
INSERT INTO `component` (`id`,`componentType`) VALUES (13,'Link');
INSERT INTO `component` (`id`,`componentType`) VALUES (14,'GPI');
INSERT INTO `component` (`id`,`componentType`) VALUES (15,'GA');
INSERT INTO `component` (`id`,`componentType`) VALUES (16,'GlobalVerdict');
INSERT INTO `component` (`id`,`componentType`) VALUES (17,'LocalVerdict');
INSERT INTO `component` (`id`,`componentType`) VALUES (18,'Sentiment');
INSERT INTO `component` (`id`,`componentType`) VALUES (19,'GA');
INSERT INTO `component` (`id`,`componentType`) VALUES (20,'GPI');

INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 12:44:43',34,'Poverty is a widespread condition in India. Since Independence, poverty is a prevalent concern. It is the twenty-first century and poverty still is a persistent menace in the country. India happens to be country wherein the disparities between the haves and the have-notes are extremely wide.It needs to be taken into account that although the economy has shown some visible signs of progress in the last two decades, this progress been uneven across various sectors or areas.','2016-07-25 12:44:43',2,'2016-07-27 12:44:42','th (5)','JPEG','2013-07-26 12:44:42','Alive','POVERTY','Permanent',1,1);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 12:44:50',12,'ndia possesses the largest illiterate population. Illiteracy in India is a problem which has complex dimensions attached to it. Illiteracy in India is more or less concerned with different forms of disparities that exist in the country.','2016-07-25 12:44:59',1,'2016-07-25 12:44:57','th (6)','JPEG','2014-07-20 12:44:57','Alive','ILLITERACY','Permanent',2,2);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-26 13:02:15',19,'Starvation can take place in a country due to many reasons like war, famine, the disparities between the rich and the poor and so on. Malnutrition conditions like kwashiorkor and marasmus can also develop into serious causes of starvation.','2016-07-26 13:02:16',9,'2016-07-26 13:02:19','th (7)','JPEG','2015-07-26 13:02:15','Hidden','STARVATION','Permanent',3,3);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 19:13:57',54,'Poverty and lack of social security are the main causes of child labour. The increasing gap between the rich and the poor, privatization of basic services and the neo-liberal economic policies are causes major sections of the population out of employment and without basic needs. This adversely affects children more than any other group.','2016-07-25 19:13:58',17,'2016-07-25 19:13:59','th (8)','JPEG','2011-07-25 19:13:57','Alive','CHILD LABOUR','Permanent',4,4);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 19:13:56',19,'The real problem lies with the society as a whole which directly or indirectly supports and encourages the system of dowry. And there seems to be no ray of hope that the situation would improve in near future.','2016-07-25 19:12:56',13,'2016-07-25 19:13:59','group-6-dowry-1-728','JPEG','2010-07-25 19:13:56','Hidden','DOWRY','Permanent',5,5);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2015-07-26 13:02:15',38,'India is a country of different religious faiths. Persons belonging to different communities.Now a day’s social media has become notorious for spread of communal hatred.','2015-07-26 13:02:16',10,'2016-07-26 13:02:15','th (9)','JPEG','2008-07-26 13:02:15','Alive','COMMUNALISM','Permanent',6,6);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2008-07-26 16:02:15',90,'Corruption in India is a major issue that adversely affects its economy.In 2015, India was ranked 76th out of 175 countries in Transparency International\'s Corruption Perceptions Index.','2009-07-26 13:02:15',69,'2050-07-26 13:02:19','th (4)','JPEG','2005-07-26 13:02:15','Alive','CORRUPTION','Permanent',7,7);

INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2008-07-26 18:02:15',79,'Mumbai has been the most preferred target for most terrorist organisations, many operating with a base from Pakistan.Over the past few years there have been a series of attacks, including explosions in Mumbai Suburban trains in July 2006, and the most recent and unprecedented attacks ','2008-07-26 19:02:15',45,'2068-07-26 13:02:15','th (10)','JPEG','2000-07-26 13:02:15','Alive','TERRORISM','Permanent',8,8);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-26 19:13:56',50,' Influx of refugees in northeast, srilankan tamil issues, border problem with Pak and china.','2016-07-25 19:13:57',23,'2016-07-25 19:13:59','th (11)','JPEG','2006-07-25 19:13:56','Alive','RELATIONSHIPS','Permanent',9,9);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-26 13:02:15',80,'Fueled by rising wages, property prices and food prices inflation in India is an increasing problem','2016-07-26 13:02:17',40,'2016-07-26 13:02:19','th','JPEG','2000-07-26 13:02:15','Alive','INFLATION','Permanent',10,10);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2015-10-01 13:02:15',60,'In India, a number of states have legislation in place to ban the slaughter of cows.  Now, in some of these states only slaughter of cow is prohibited which means buffaloes, bulls, bullocks, and other cattle may be slaughtered for consumption.','2015-10-16 13:02:17',45,'2015-11-16 13:02:15','th (12)','JPEG','2015-10-16 13:02:15','Expired','BEEF BAN','Temporary',11,11);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2014-05-15 13:00:00',20,'rticle 370 of the Indian constitution is an article that grants special autonomous status to the state of Jammu and Kashmir.According to this article, except for defence, foreign affairs, finance and communications, Parliament needs the state government\'s concurrence for applying all other laws.','2014-06-15 13:00:00',7,'2015-05-15 13:00:00','download','png','1947-08-15 12:00:00','Expired','ARTICLE 370','Temporary',12,12);

INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2015-12-15 12:00:00',99,' Odd-even rule: Cars with odd numbers will be allowed on roads between April 15, 17, 19, 21, 23, 25, 27, 29 (Sunday is open for all), while those with even numbers will be allowed on roads between April 16, 18, 20, 22, 24, 26 and so on.','2016-04-30 12:00:00',40,'2016-06-30 12:00:00','youtube.com_','JPEG','2016-01-01 12:00:00','Expired','EVEN ODD','Temporary',13,13);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-10 13:13:09',100,' students from the Jawaharlal Nehru University organised an event on Parliament attack convict Afzal Guru who was hanged in 2013. This was to be done the day after Guru’s third death anniversary.The event organisers had pasted posters across the campus inviting students to gather for a protest march against “judicial killing of Afzal Guru and Maqbool Bhat”','2016-07-13 13:13:09',79,'2016-07-29 13:13:09','download (1)','JPEG','2016-07-09 13:13:09','Expired','JNU ISSUE','Temporary',14,14);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 00:00:00',87,'Sixteen years after starting her hunger strike demanding the repeal of the Armed Forces (Special Powers) Act, Irom Chanu Sharmila has decided to end it on August 9 and contest the Manipur Assembly elections as an Independent candidate.','2016-07-27 00:00:00',67,'2016-08-09 00:00:00','images','JPEG','2000-08-09 00:00:00','Alive','IROM SHARMILA TO END FAST','Temporary',15,15);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-02-01 00:00:00',50,' Jat Reservation Agitation was a series of protests in February 2016 by Jat people of North India, especially those in the state of Haryana, which \"paralysed the State for 10 days.','2016-02-08 00:00:00',20,'2016-02-01 00:00:00','download (2)','JPEG','2016-02-02 00:00:00','Expired','JAT RESERVATION','Temporary',16,16);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2014-10-07 00:00:00',79,'Swachh Bharat Abhiyan  is a national campaign by the Government of India, covering 4,041 statutory cities and towns, to clean the streets, roads and infrastructure of the country','2014-10-09 00:00:00',68,'2014-11-02 00:00:00','images (1)','JPEG','2014-10-02 00:00:00','Expired','SWACH BHART ABHIYAAN','Temporary',17,17);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 19:13:51',20,' doping refers to the use of banned athletic performance-enhancing drugs by athletic competitors, where the term doping is widely used by organizations that regulate sporting competitions. The use of drugs to enhance performance is considered unethical','2016-07-25 19:13:58',16,'2016-07-25 19:13:58','thth','JPEG','2016-07-25 19:13:59','Alive','DOPING IN SPORT','Temporary',18,18);
INSERT INTO `sentiment` (`createTime`,`created_By`,`description`,`editTime`,`editedBy`,`endTime`,`image`,`imageType`,`startTime`,`state`,`subject`,`type`,`id`,`permanentSentiments_id`) VALUES ('2016-07-25 19:13:51',56,'Environmentalists have expressed concern that the number of people participating in the Amarnath Yatra is having a negative impact on the area\'s ecology and some have expressed support for government regulated limits on the number of pilgrims permitted to make the trek','2016-07-25 19:13:58',19,'2016-07-25 19:13:58','images (2)','JPEG','2016-07-25 19:13:59','Alive','AMARNATH YATRA','Temporary',19,19);






INSERT INTO `poll` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`question`,`state`,`sentimentId`) VALUES (2,'2016-08-11 19:54:26',10,'this is the description','2016-08-11 19:54:26',NULL,'What is the test question?','Active',1);
INSERT INTO `poll` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`question`,`state`,`sentimentId`) VALUES (3,'2016-08-11 19:54:27',10,'this is the description','2016-08-11 19:54:27',NULL,'What is the test question?','Active',2);
INSERT INTO `poll` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`question`,`state`,`sentimentId`) VALUES (4,'2016-08-11 19:55:07',10,'this is the description','2016-08-11 19:55:07',NULL,'What is the test question?','Active',3);
INSERT INTO `poll` (`id`,`createTime`,`createdBy`,`description`,`editTime`,`editedBy`,`question`,`state`,`sentimentId`) VALUES (5,'2016-08-11 19:55:08',10,'this is the description','2016-08-11 19:55:08',NULL,'What is the test question?','Active',4);

INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (3,'2016-08-11 12:54:25',10,'2016-08-11 12:54:25',NULL,'Option 1','Active',2);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (4,'2016-08-11 12:54:25',10,'2016-08-11 12:54:25',NULL,'Option 2','Active',2);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (5,'2016-08-11 12:54:27',10,'2016-08-11 12:54:27',NULL,'Option 1','Active',3);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (6,'2016-08-11 12:54:27',10,'2016-08-11 12:54:27',NULL,'Option 2','Active',3);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (7,'2016-08-11 12:55:07',10,'2016-08-11 12:55:07',NULL,'Option 1','Active',4);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (8,'2016-08-11 12:55:07',10,'2016-08-11 12:55:07',NULL,'Option 2','Active',4);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (9,'2016-08-11 12:55:08',10,'2016-08-11 12:55:08',NULL,'Option 1','Active',5);
INSERT INTO `PollOption` (`id`,`createTime`,`createdBy`,`editTime`,`editedBy`,`PollOption`,`state`,`poll`) VALUES (10,'2016-08-11 12:55:08',10,'2016-08-11 12:55:08',NULL,'Option 2','Active',5);
