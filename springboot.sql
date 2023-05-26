/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 5.6.21-log : Database - springboot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springboot` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springboot`;

/*Table structure for table `addcourse` */

DROP TABLE IF EXISTS `addcourse`;

CREATE TABLE `addcourse` (
  `username` varchar(20) DEFAULT NULL,
  `courseisbn` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `addcourse` */

insert  into `addcourse`(`username`,`courseisbn`) values 
('uzzjsj',2018121901),
('uzzjsj',1043678),
('uzzjsj',55555),
('uzzjsj',201908112),
('uzzjsj',2018121903),
('tianxiaohua',11111111),
('tianxiaohua',2018121902),
('tianxiaohua',2018121905),
('tianxiaohua',2018121906),
('tianxiaohua',2019002356),
('caojie',2018121905),
('caojie',666),
('caojie',1043678),
('caojie',2018121901),
('caojie',11111111),
('caojie',2019030322),
('caojie',2019030321),
('caojie',2049123456),
('caojie',2019458789),
('wangkaili',33333),
('wangkaili',1043678),
('wangkaili',201908112),
('wangkaili',2018121904),
('wangkaili',201908112),
('wangkaili',2018121903),
('wangkaili',2019030322),
('wangkaili',2019030321),
('wangkaili',2019123456),
('wangkaili',2019030322),
('wangkaili',55555),
('chenbaihe',666),
('chenbaihe',55555),
('chenbaihe',1043678),
('chenbaihe',11111111),
('chenbaihe',201908112),
('chenbaihe',1043678),
('chenbaihe',2019123456),
('chenbaihe',11111111),
('chenbaihe',201908112),
('chenbaihe',2019458789),
('chenbaihe',2019002356),
('chenbaihe',2018121909),
('uzzjsj',33333);

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(32) NOT NULL,
  `CourseISBN` int(32) NOT NULL,
  `Entrytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

/*Data for the table `cart` */

insert  into `cart`(`id`,`UserISBN`,`CourseISBN`,`Entrytime`) values 
(75,76,11111111,'2021-03-07 11:25:40');

/*Table structure for table `chapter` */

DROP TABLE IF EXISTS `chapter`;

CREATE TABLE `chapter` (
  `ChapterId` int(16) NOT NULL AUTO_INCREMENT,
  `CourseISBN` int(26) NOT NULL,
  `ChapterHao` int(16) NOT NULL,
  `chapter` varchar(100) NOT NULL,
  `kaoshitime` timestamp NULL DEFAULT NULL,
  `test` int(1) DEFAULT '0',
  PRIMARY KEY (`ChapterId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `chapter` */

insert  into `chapter`(`ChapterId`,`CourseISBN`,`ChapterHao`,`chapter`,`kaoshitime`,`test`) values 
(1,666,1,'Photoshop初认识','2021-03-01 00:00:00',1),
(4,666,3,'Photoshop工具介绍','2021-02-28 00:00:01',0),
(8,666,2,'Photoshop的p图操作','2021-02-28 00:00:02',1),
(12,33333,1,'互联网','2021-07-10 00:00:00',0);

/*Table structure for table `commentt` */

DROP TABLE IF EXISTS `commentt`;

CREATE TABLE `commentt` (
  `CommentID` int(26) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(26) NOT NULL,
  `CourseISBN` int(26) NOT NULL,
  `Grade` float NOT NULL,
  `Comment` varchar(100) DEFAULT NULL,
  `Entrytime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`CommentID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `commentt` */

insert  into `commentt`(`CommentID`,`UserISBN`,`CourseISBN`,`Grade`,`Comment`,`Entrytime`) values 
(1,76,666,3,'秋意渐浓。秋风是个调皮的孩子，在山坡、田野不经意的嬉戏间，洒下金黄与深红，整个村庄就被渲染成了一幅壮美油画。我知道，属于父亲的季节来了。','2021-02-19 11:37:22'),
(2,77,666,4.5,'秋意渐浓。秋风是个调皮的孩子，在山坡、田野不经意的嬉戏间，洒下金黄与深红，整个村庄就被渲染成了一幅壮美油画。我知道，属于父亲的季节来了。','2021-02-19 12:05:20'),
(3,76,666,1.5,'秋意渐浓。秋风是个调皮的孩子，在山坡、田野不经意的嬉戏间，洒下金黄与深红，整个村庄就被渲染成了一幅壮美油画。我知道，属于父亲的季节来了。','2021-02-19 13:21:23'),
(5,76,666,5,'秋意渐浓。秋风是个调皮的孩子，在山坡、田野不经意的嬉戏间，洒下金黄与深红，整个村庄就被渲染成了一幅壮美油画。我知道，属于父亲的季节来了。','2021-02-19 13:21:50'),
(9,76,2018121902,4,'131231','2021-02-19 19:36:53'),
(12,76,55555,4,'12312312312','2021-02-19 20:48:00'),
(13,76,666,4,'1312','2021-02-19 22:23:21'),
(14,76,666,4,'123123','2021-02-19 22:29:17'),
(15,76,666,4,'243','2021-02-19 22:40:21'),
(17,76,666,4,'1231231','2021-02-19 23:01:45'),
(18,76,666,5,'','2021-02-19 23:05:47'),
(19,76,666,0.5,'131','2021-02-19 23:07:07');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `ISBN` int(20) NOT NULL AUTO_INCREMENT,
  `CourseName` varchar(36) DEFAULT NULL,
  `CreatorName` varchar(36) DEFAULT NULL,
  `CreatorTel` int(16) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL,
  `Decription` text,
  `Img` varchar(50) DEFAULT NULL,
  `CategoryId` int(6) DEFAULT NULL,
  `Entrytime` timestamp NULL DEFAULT NULL,
  `SpecialCourse` int(3) DEFAULT NULL,
  PRIMARY KEY (`ISBN`),
  KEY `CategoryId` (`CategoryId`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`CategoryId`) REFERENCES `coursecategory` (`CategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=2049123461 DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`ISBN`,`CourseName`,`CreatorName`,`CreatorTel`,`Price`,`Decription`,`Img`,`CategoryId`,`Entrytime`,`SpecialCourse`) values 
(666,'Photoshop平面设计','aaa',76,121,'系统地介绍了Photoshop CS3的基本功能、操作方法和应用技巧。本书共分10章，主要包括Photoshop CS3基础知识、立体几何图形绘制、数码相机和Photoshop的结合、图层的运用、路径的运用、通道和蒙版的运用、滤镜的运用、文字特效、Photoshop在网页中的应用、动作的运用等。','img/course/1.jpg',1,'2020-09-13 20:55:31',3),
(33333,'计算机网络实验','aaa',123132,112,'谢希仁著的《计算机网络（第七版）》配套视频课程，包括计算机网络概述、物理层、数据链路层、网络层、运输层、应用层、网络安全、互联网上的音视频服务、无线网络和移动网络等九个章节的一一对应的课程。','img/course/2.jpg',1,'2020-09-13 21:11:30',3),
(55555,'计算机组装与维护','aa',1231231,131,'从案例入手，由浅入深、由易到难组织教学内容。主要介绍主机（主板、中央处理器、内存条、电源与机箱）、存储设备、输入/输出设备等主要配件的识别、安装和日常维护；重点介绍如何安装、调试硬件以及如何安装操作系统等基本操作；同时还介绍常用工具软件的使用方法和微型计算机常见故障维修。','img/course/3.jpg',1,'2020-09-13 20:52:43',1),
(1043678,'新手学电脑','aaa',1876920203,67,'首先让读者学习和了解了电脑的基础知识、Windows Vista操作系统、鼠标和键盘的使用，以及汉字的输入方法，在此基础上再向读者介绍了在Windows Vista操作系统下要如何对文件进行管理、对操作系统进行个性化设置，以及如何使用系统的常用附件。接下来向读者介绍了如何安装和使用软件，详细介绍了Office办公软件中的Word、Excel和PowerPoint的使用。最后向读者展现了丰富精彩的网络生活，并介绍了电脑的安全与维护方面的基础知识。','img/course/5.jpg',1,'2019-04-01 10:41:11',1),
(11111111,'计算机基础实用','aaa',789456,13,'《计算机基础实用教程》以Windows7和Office2010为系统环境，分为五个模块，模块一介绍计算机的认识和组装；模块二介绍计算机操作系统——Window7；模块三介绍文字处理软件——Word2010；模块四介绍表格处理软件——Excel2010；模块五介绍演示文稿软件——PowerPoint2010。《计算机基础实用教程》内容涉及的知识面广，其内容体现了循序渐进、由浅入深的思想和理念，适合分级教学，以满足不同学时、不同基础读者的学习需求。在教学实践中，教师可根据学时数和学生的基础来选择内容，读者可依据自身的兴趣和学习需求选择实验内容进行自主实验。 [1] ','img/course/4.jpg',1,'2020-09-13 20:48:55',1),
(201908112,'Java编程思想','aaa',1876543342,0,'本课程赢得了全球程序员的广泛赞誉，即使是最晦涩的概念，在Bruce Eckel的文字亲和力和小而直接的编程示例面前也会化解于无形。从Java的基础语法到最高级特性（深入的面向对象概念、多线程、自动项目构建、单元测试和调试等），本书都能逐步指导你轻松掌握。','img/course/6.jpg',1,'2019-03-31 04:42:12',3),
(2018121900,'北方文学','aaa',1825379821,13,'向广大文学理论研究者、科研工作者、文教工作者等，征集文学、理论、文教、企业文化等各个方面的稿件和学术论文。 [1] \r\n','img/course/11.jpg',2,'2020-09-13 09:10:39',1),
(2018121901,'战争与和平','aa',1876920203,0,'该课程为《战争与和平》是俄国作家列夫·尼古拉耶维奇·托尔斯泰创作的长篇小说，也是其代表作，创作于1863—1869年。\r\n该作以1812年的卫国战争为中心，反映从1805到1820年间的重大历史事件。以鲍尔康斯、别祖霍夫、罗斯托夫和库拉金四大贵族的经历为主线，在战争与和平的交替描写中把众多的事件和人物串联起来。 [1] ','img/course/12.jpg',2,'2020-09-06 04:06:20',1),
(2018121902,'活着','aa',789456,13,'福贵（葛优饰）是一个嗜赌如命的纨绔子弟，把家底儿全输光了，老爹也气死了\r\n《活着》海报\r\n《活着》海报(5张)\r\n，怀孕的妻子家珍（巩俐饰）带着女儿凤霞离家出走，一年之后又带着新生的儿子有庆回来了。福贵从此洗心革面，和同村的春生一起操起了皮影戏的营生，却被国民党军队拉了壮丁，后来又糊里糊涂的当了共产党的俘虏。他们约定，一定要活着回去。历尽千辛万苦，终于平安回到家中，母亲却已去逝，女儿凤霞也因生病变哑了。','img/course/13.jpg',2,'2020-09-06 04:06:58',1),
(2018121903,'傲慢与偏见','aa',1876543342,13,'本课程主要讲述《傲慢与偏见》是根据简·奥斯汀同名小说改编，由焦点电影公司发行的一部爱情片，由乔·怀特执导，凯拉·奈特利、马修·麦克费登、唐纳德·萨瑟兰等联合主演。该片于2005年9月16日在英国上映。该片讲述了19世纪初期英国的乡绅之女伊丽莎白·班内特五姐妹的爱情与择偶的故事。 >>>','img/course/14.jpg',2,'2020-09-12 02:26:43',1),
(2018121904,'外交学','aa',1876543342,13,'当前中国的国际地位大大提升，中国的声音在国际社会日益受到重视。中国的外交实践每天都在发展，每天也都遇到新的问题，需要总结，需要对策研究和战略研究。为此，我们要总结这种趋势从理论高度提出中国学者的看法。\r\n在编写过程中，作者团队认真学习毛泽东、周恩来、邓小平、江泽民等党和国家领导人的外交思想和实践，学习外交战线上老外交家和实际工作者的外交实践经验，并参考和借鉴国内外学者关于外交学和外交业务与技术的科研成果，坚持以马列主义、毛泽东思想和邓小平理论以及“三个代表”重要思想为指导，按照分工，努力完成自己的任务。','img/course/15.jpg',2,'2020-09-12 02:30:42',1),
(2018121905,'德育原理','aa',1825379821,32,'\r\n本课程立足于当代学校德育中存在的理论与实践问题，对学校德育理论与实践的基本原理进行了系统梳理。整个教材以对道德的概念界说为基础，强调了道德的生命基础与实践意义，阐述了德育的内在要素、结构与功能。','img/course/16.jpg',2,'2020-09-12 02:51:28',2),
(2018121906,'大学英语 泛读','a',1,13,'该课程《大学英语》系列教材的再修订，以《大学英语泛读1(学生用书)(第3版)》为依据，历经三年调研，汲取全国数百所高校师生的建议和意见，旨在发扬我国大学英语教学的优良传统，推广成功经验，为新时期人才培养再作贡献。 [1]  ','img/course/21.jpg',3,'2020-09-13 09:01:24',2),
(2018121907,'大学英语 听说','aa',1825379821,13,'该课程《大学英语:听说(第2册)(学生用书)(第3版)》为听说教程第二册的学生用书的课程。《大学英语:听说(第2册)(学生用书)(第3版)》共十六个单元，设八个话题。十六个单元后提供两套综合试题。书末附录提供课文听力训练的音带原文及练习答案。每单元设A、B、C、D四个部分：Part A“微技能训练”，这部分提供两个练习，旨在帮助学生获取重要的交际能力和掌握多种听力技能。Part B“语篇训练”，这部分提供两篇听力材料。Part C“口语训练”，这部分的口语活动主要围绕课文内容及各种交际功能展开。Part D“课外听力训练”，这部分提供一篇与课文同一话题的听力材料及练习，供学生课外自学。 [1] ','img/course/22.jpg',3,'2020-09-13 09:06:34',2),
(2018121909,'英语教学策略论','a',1876920203,13,'该课程系统介绍了“课堂组织”、“课堂评估”、“学习激励”及“策略培养”等宏观教学原则和微观实施措施。','img/course/23.jpg',3,'2020-09-13 09:12:53',0),
(2019002356,'健康中国茶','aaa',1,13,'谚语有云：清晨一杯茶，饿死卖药家。饮茶有益健康，是当今文化界和科学界的普遍共识。中国茶叶品类丰富，依据加工和储藏方法的不同，有绿茶、黄茶、白茶、乌龙、红茶、黑茶、普洱、花茶、老茶之别。各品类茶保健功效多有不同，没有一种茶兼具所有保健功能，而每一种茶都有特定功效。','img/course/42.jpg',NULL,'2021-02-25 20:10:54',2),
(2019030321,'新视界大学英语','a',1825379821,13,'该课程《新视界大学英语1:综合教程》为大学英语基础阶段综合教程，共8个单元，包括大学生活、饮食文化、思维方式、人际交往、家庭、旅游、环境等大学生感兴趣的话题，让学生在学语言的同时，了解其他国家的文化，培养批判思维习惯，提高其综合素质。《新视界大学英语》是由外语教学与研究出版社和英国麦克米伦出版公司合作开发、中外英语教育专家共同设计、国内多所高校教师参与编写的一套国际化、立体化大学英语教材。','img/course/24.jpg',3,'2019-03-31 04:46:37',2),
(2019030322,'大学俄语专题语法','a',1876920203,43,'适合高等院校俄语院系师生进行实践语法教学以及备考全国高校俄语专业四、八级考试使用。本书以语法专题训练为主， [1]  书中的内容包括基础阶段语法知识及提高阶段语法难点。本书的使用者应掌握一定的基础语法知识，书中所列章节不作为语法体系分类的原则。 [1] 大学俄语专题语法（本文作者不为该书作者）','img/course/25.jpg',3,'2019-03-31 04:43:42',0),
(2019123456,'赏兰','a',1,132,'循着既有的兴趣与理想，往返于台湾各地山区，试着更深入密林禁地，寻访潜匿于云山幽谷中的兰踪。两年馀的时间里，有了更深入更广泛的收获，并追踪摸索出十馀种尚未记录的兰种。','img/course/31.jpg',4,'2021-06-01 20:10:56',2),
(2019234567,'俄语新论','aa',1,45,'旨在从不同的角度用不同的方法解决同一个问题：语言表述的使用，在什么情况下，在多大程度上取决于语言所表示的那个语言外现实情况的客观特性。这一问题的解决与一系列的问题紧密联系在一起:在世界的语言图景中，主观与客观、认知与交际、逻辑与实用的相互关系问题和相互作用问题。对这些问题的描写在很大程度上就是对语言的语义的描写。因此，现代语义学研究相当严谨地同时涉及两个方向:1）研究现实，分析社会哲学概念;2）描写间接利用语言的各种不同方法。 [1] ','img/course/26.jpg',3,'2019-03-06 19:53:25',0),
(2019458789,'健美操','a',1876543342,311,'健美操是一项深受广大群众喜爱的、普及性极强的，集体操、舞蹈、音乐、健身、娱乐于一体的体育项目。\r\n健美操中大量吸收了迪斯科舞、爵士舞、霹雳舞中的上下肢、躯干、头颈和足踩动作，特别是髋部动作，这给健美操增添了活力，同时也有利于减少臀部和腹部脂肪的堆积，有利于改善动作的协调性和灵活性。','img/course/41.jpg',5,'2021-02-25 20:11:00',0),
(2049123456,'草木花卉','aa',1876543342,131,'当你的宝宝满了一周岁，智力和体能都快速地发展，这是进入幼儿期的一个关键时期，如独自站立，开口说话，认识物体等。在孩子的眼里，这个世界充满了新奇与陌生，他对这个世界上的所有物体都充满了感知欲、好奇心。这时，给他正确的引导和悉心的培养，将能确保你的宝宝得以健康的成长。这套书集图形图像认知、文字结构、文字发音、中英文对照和儿歌朗诵等多种功能于一体，从色彩、形状、文字和语言上给予宝宝良好的刺激。是你宝宝成长的关键时期认知世界的第一扇窗口。','img/course/32.jpg',4,'2021-02-25 20:11:03',3);

/*Table structure for table `coursecategory` */

DROP TABLE IF EXISTS `coursecategory`;

CREATE TABLE `coursecategory` (
  `CategoryId` int(6) NOT NULL,
  `CategoryName` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`CategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `coursecategory` */

insert  into `coursecategory`(`CategoryId`,`CategoryName`) values 
(1,'计算机'),
(2,'文学'),
(3,'外语'),
(4,'农林园艺'),
(5,'健康运动');

/*Table structure for table `examresult` */

DROP TABLE IF EXISTS `examresult`;

CREATE TABLE `examresult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseisbn` int(16) DEFAULT NULL,
  `chapterid` int(16) DEFAULT NULL,
  `userisbn` int(16) DEFAULT NULL,
  `radioscores` int(11) DEFAULT NULL,
  `checkscores` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

/*Data for the table `examresult` */

insert  into `examresult`(`id`,`courseisbn`,`chapterid`,`userisbn`,`radioscores`,`checkscores`,`total`,`createtime`) values 
(74,666,1,76,40,20,60,'2021-02-28 23:21:29');

/*Table structure for table `orderlist` */

DROP TABLE IF EXISTS `orderlist`;

CREATE TABLE `orderlist` (
  `orderid` int(26) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(26) DEFAULT NULL,
  `UserTel` varchar(26) DEFAULT NULL,
  `CourseISBN` int(26) DEFAULT NULL,
  `CoursePrice` int(10) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `Entrytime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB AUTO_INCREMENT=1613630731 DEFAULT CHARSET=utf8;

/*Data for the table `orderlist` */

insert  into `orderlist`(`orderid`,`UserISBN`,`UserTel`,`CourseISBN`,`CoursePrice`,`note`,`Entrytime`) values 
(1613630721,76,'18253798217',666,121,'12312312','2021-02-19 13:19:52');

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `courseisbn` int(26) DEFAULT NULL,
  `chapterid` int(11) DEFAULT NULL,
  `optiona` varchar(255) DEFAULT NULL,
  `optionb` varchar(255) DEFAULT NULL,
  `optionc` varchar(255) DEFAULT NULL,
  `optiond` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `questions` */

insert  into `questions`(`id`,`subject`,`type`,`courseisbn`,`chapterid`,`optiona`,`optionb`,`optionc`,`optiond`,`answer`) values 
(1,'语文难不难？？？？？','单选',666,1,'难','不难','一般般','很简单','A'),
(2,'语文为什么难？','多选',666,1,'老师太凶了','看不懂文言文','我是理科男','情商不高','A,B,C,D'),
(3,'以下哪些是柳宗元的诗？','多选',666,1,'捕蛇者说','静夜思','永州八记','咏鹅','A,C'),
(4,'柳宗元被贬在哪里的时候作了很多诗？','单选',666,1,'永州','长沙','南昌','柳州','A'),
(5,'历史上最快的人的是谁？','单选',666,1,'曹操','张飞','关羽','刘备','A'),
(6,'《静夜思》是谁写的？','单选',666,2,'柳宗元','周敦颐','老子','李白','D'),
(7,'《爱莲说》是谁写的？','单选',666,4,'柳宗元','周敦颐','李白','陶渊明','B'),
(8,'以下哪个词语是形容友情的？','单选',NULL,2,'情意绵绵','情同手足','海枯石烂','天涯海角','B'),
(9,'1+1等于多少？','单选',NULL,4,'1','2','3','4','B'),
(10,'2+2等于多少？','单选',666,4,'1','2','3','4','D'),
(11,'11+11等于多少？','单选',NULL,4,'11','33','22','44','C'),
(12,'66+66等于多少？','单选',NULL,4,'132','145','142','162','A'),
(13,'77+11等于多少？','单选',NULL,4,'22','33','88','143','C'),
(14,'6+1等于多少？','单选',666,1,'1','3','5','7','D'),
(15,'比3小的数是？','多选',NULL,4,'1','2','3','4','A,B'),
(16,'比5大的数是？','多选',NULL,4,'2','4','6','8','C,D'),
(17,'my是什么意思？','单选',NULL,5,'你的','我的','你们的','我们的','B'),
(18,'he是什么意思？','单选',666,2,'你','我','他','她','C'),
(19,'her是什么意思？','单选',NULL,5,'你','我','他','她','D'),
(20,'I是第几人称？','单选',NULL,5,'第一人称','第二人称','第三人称','第四人称','A'),
(21,'want是什么意思？','单选',NULL,5,'想要','得到','我们','全部','A'),
(22,'love是什么意思？','单选',NULL,5,'恨','爱','交','加','B'),
(23,'以下哪些是第三人称','多选',NULL,5,'he','her','we','I','A,B'),
(24,'以下哪些是打招呼用语？','多选',NULL,5,'hi','hey','like','would','A,B'),
(27,'溺水时','单选',666,1,'1','2','3','4','A'),
(28,'计算机网络的概念','单选',33333,1,'是网络','是计算机','是网','计算机网络就是通过线路互连起来的、资质的计算机集合','D'),
(29,'计算机网络的定义','多选',33333,1,'计算机网络就是通过线路互连起来的、资质的计算机集合','网络','计算机','将分布在不同地理位置上的具有独立工作能力的计算机、终端及其附属设备用通信设备和通信线路连接起来','A,D'),
(30,'photoshop的软件是什么？','单选',666,2,'QQ','钉钉','PS','app','C'),
(31,'删除操作','多选',666,2,'delete','删除','添加','修改','A,B');

/*Table structure for table `smallchapter` */

DROP TABLE IF EXISTS `smallchapter`;

CREATE TABLE `smallchapter` (
  `SmallChapterId` int(16) NOT NULL AUTO_INCREMENT,
  `ChapterHao1` int(16) NOT NULL,
  `ChapterHao2` int(16) NOT NULL,
  `CourseISBN` int(26) NOT NULL,
  `SmallChapter` varchar(100) NOT NULL,
  `video1Name` varchar(100) DEFAULT NULL,
  `video1` varchar(50) DEFAULT NULL,
  `video2Name` varchar(50) DEFAULT NULL,
  `video2` varchar(50) DEFAULT NULL,
  `work1Name` varchar(50) DEFAULT NULL,
  `work1time` timestamp NULL DEFAULT NULL,
  `work1` varchar(50) DEFAULT NULL,
  `work2Name` varchar(50) DEFAULT NULL,
  `work2time` timestamp NULL DEFAULT NULL,
  `work2` varchar(50) DEFAULT NULL,
  `grade` int(16) DEFAULT NULL,
  PRIMARY KEY (`SmallChapterId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `smallchapter` */

insert  into `smallchapter`(`SmallChapterId`,`ChapterHao1`,`ChapterHao2`,`CourseISBN`,`SmallChapter`,`video1Name`,`video1`,`video2Name`,`video2`,`work1Name`,`work1time`,`work1`,`work2Name`,`work2time`,`work2`,`grade`) values 
(1,1,1,666,'为什么要学习Photoshop？','为什么要学习Photoshop？','1.mp4','学习Photoshop？','1.mp4','Photoshop作业','2021-02-28 01:21:06','1.pdf','','2021-02-27 11:35:08','',20),
(2,1,2,666,'Photoshop的作用是什么？','作业视频','1.mp4',NULL,NULL,'作业','2021-02-19 11:34:56','1.pdf','作业2','2021-02-28 01:21:22','1.pdf',30),
(4,1,3,666,'如何学号Photoshop','11','1.mp4','11','1.mp4','','2021-02-28 01:33:18','',NULL,NULL,NULL,4),
(7,1,1,33333,'计算机网络的定义及特点','定义及特点配套视频','3.mp4',NULL,NULL,'计算机网络的定义','2021-03-07 21:54:10','1.pdf',NULL,NULL,NULL,20),
(8,1,2,33333,'互联网的概念','互联网的概念','3.mp4',NULL,NULL,'互联网概念作业','2021-03-07 21:54:23','1.pdf',NULL,NULL,NULL,50);

/*Table structure for table `studyspace` */

DROP TABLE IF EXISTS `studyspace`;

CREATE TABLE `studyspace` (
  `id` int(26) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(26) DEFAULT NULL,
  `UserTel` varchar(26) DEFAULT NULL,
  `CourseISBN` int(26) DEFAULT NULL,
  `Entrytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1613630731 DEFAULT CHARSET=utf8;

/*Data for the table `studyspace` */

insert  into `studyspace`(`id`,`UserISBN`,`UserTel`,`CourseISBN`,`Entrytime`) values 
(1,76,'18253798217',666,'2021-03-01 12:54:43');

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `TestId` int(16) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(16) NOT NULL,
  `UserName` varchar(50) NOT NULL,
  `CourseISBN` int(16) NOT NULL,
  `ChapterId` int(16) NOT NULL,
  `SmallChapterId` int(16) NOT NULL,
  `Work1Name` varchar(50) NOT NULL,
  `Work1File` varchar(50) NOT NULL,
  `Work1Time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `Work2Name` varchar(50) DEFAULT NULL,
  `Work2File` varchar(50) DEFAULT NULL,
  `Work2Time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `grade` int(16) DEFAULT NULL,
  PRIMARY KEY (`TestId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `test` */

insert  into `test`(`TestId`,`UserISBN`,`UserName`,`CourseISBN`,`ChapterId`,`SmallChapterId`,`Work1Name`,`Work1File`,`Work1Time`,`Work2Name`,`Work2File`,`Work2Time`,`grade`) values 
(6,71,'admin',666,1,1,'zuoyw','1614225127990.pdf','2021-03-01 11:21:48','werwqr','1614225135347.pdf','2021-02-25 11:52:15',80),
(7,76,'戒躁_T',666,1,1,'zuoyw134作业','2.pdf','2021-03-01 11:21:51',NULL,NULL,'0000-00-00 00:00:00',60),
(8,76,'戒躁_T',666,1,2,'dfsdf','1614522389674.pdf','2021-03-01 11:26:48','作业2','','2021-02-28 22:26:36',NULL);

/*Table structure for table `userdetail` */

DROP TABLE IF EXISTS `userdetail`;

CREATE TABLE `userdetail` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `password` varchar(16) NOT NULL,
  `sex` int(1) DEFAULT '1',
  `data` timestamp NULL DEFAULT NULL,
  `img` varchar(50) DEFAULT 'img/user/user.jpg',
  `email` varchar(32) DEFAULT NULL,
  `telephone` varchar(12) DEFAULT NULL,
  `company` varchar(32) DEFAULT NULL,
  `address` varchar(64) DEFAULT NULL,
  `role` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Data for the table `userdetail` */

insert  into `userdetail`(`id`,`username`,`password`,`sex`,`data`,`img`,`email`,`telephone`,`company`,`address`,`role`) values 
(59,'Tian44433','123456',1,'2021-02-18 21:17:08','img/user/user.jpg','13958122@qq.com','18253798214',NULL,NULL,0),
(61,'33_T','123456',2,'2021-01-18 22:30:16','img/user/user.jpg','1395813022@qq.com','13553181213',NULL,NULL,0),
(66,'33344','123456',1,'2021-01-18 10:22:29','img/user/user.jpg','1395813022@qq.com','18232390007',NULL,NULL,0),
(69,'admin','1234564352345',2,'2021-01-18 22:45:32','img/user/user.jpg','12@qq.com','12',NULL,NULL,0),
(71,'admin','123456',1,'2021-01-18 20:05:26','img/user/user.jpg','11@qq.com','123123',NULL,NULL,0),
(72,'admin','123456',1,'2021-01-18 20:15:34','img/user/user.jpg','1@qq.com','1231231',NULL,NULL,0),
(73,'admin','123456',2,'2021-01-18 20:17:49','img/user/user.jpg','123@qq.com','142314',NULL,NULL,0),
(74,'admin','123456',2,'2021-01-18 22:29:25','img/user/user.jpg','24352345@qq.com','345253',NULL,NULL,0),
(75,'admin','123456',2,'2021-01-18 22:29:46','img/user/user.jpg','12@qq.com','1111',NULL,NULL,0),
(76,'戒躁_T','123456',2,'2021-01-19 12:36:40','img/user/user.jpg','1962224652@qq.com','12345678901',NULL,NULL,1),
(77,'戒躁_T','123456',1,'2021-01-19 13:37:16','img/user/user.jpg','18253798217@qq.com','18769202036',NULL,NULL,0),
(78,'戒躁_T','123456',1,'2021-01-19 21:05:15','img/user/user.jpg','18769202036@qq.com','13553128613',NULL,NULL,0),
(79,'戒躁_T','123456',1,'2021-01-19 21:35:14','img/user/user.jpg','18769202036@qq.com','13546188613',NULL,NULL,0),
(80,'18253798217','123456',1,'2021-02-08 23:50:22','img/user/user.jpg','1962224652@qq.com','18211118217',NULL,NULL,0),
(81,'18253798217','123456',2,'2021-02-08 23:55:25','img/user/user.jpg','1962224652@qq.com','18253798223',NULL,NULL,0),
(82,'18253798217','123456',2,'2021-02-08 23:58:06','img/user/user.jpg','1962224652@qq.com','18253727',NULL,NULL,0),
(83,'123','123456',2,'2021-02-20 12:01:50','img/user/user.jpg','1962224652@qq.com','181312218217',NULL,NULL,0),
(84,'18253798217','123456',1,'2021-02-20 12:02:23','img/user/user.jpg','1962224652@qq.com','182537123121',NULL,NULL,0),
(85,'tiand22','123456',1,'2021-02-20 12:04:06','img/user/user.jpg','1962224652@qq.com','13463798217',NULL,NULL,0),
(86,'tiand22','123456',1,'2021-02-20 13:07:02','img/user/user.jpg','1962224652@qq.com','18253798123',NULL,NULL,0),
(87,'18253798217','123456',1,'2021-02-20 13:30:33','img/user/user.jpg','1962224652@qq.com','18253135717',NULL,NULL,0);

/*Table structure for table `wish` */

DROP TABLE IF EXISTS `wish`;

CREATE TABLE `wish` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `UserISBN` int(32) NOT NULL,
  `CourseISBN` int(32) NOT NULL,
  `Entrytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `wish` */

insert  into `wish`(`id`,`UserISBN`,`CourseISBN`,`Entrytime`) values 
(1,76,55555,'2021-02-16 21:18:24'),
(2,76,666,'2021-02-16 21:20:30'),
(5,76,2019123456,'2021-02-17 10:43:26'),
(6,76,2018121900,'2021-02-17 10:46:08'),
(15,76,11111111,'2021-03-07 11:25:29');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
