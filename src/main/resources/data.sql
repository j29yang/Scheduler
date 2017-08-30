/** table "t_project" */
/**INSERT INTO `t_project` (`id`,`description`,`name`) VALUES (1,'demo project desc1','demo project1'),(2,'demo project desc2','demo project2'),(3,'demo project desc3','demo project3'),(4,'demo project desc4','demo project4'),(5,'demo project desc5','demo project5'),(6,'demo project desc6','demo project6'),(7,'demo project desc7','demo project7'),(8,'demo project desc8','demo project8'),(9,'demo project desc9','demo project9'),(10,'demo project desc10','demo project10');*/
INSERT INTO `t_project` (`description`,`name`) VALUES ('TDLTE','TDLTE');

/** table "t_role" */
INSERT INTO `t_role` (`role_id`,`description`,`name`) VALUES (1,'root desc','root'),(2,'member desc','member');

/** table "t_server" */
INSERT INTO `t_server` (`address`,`password`,`server`,`user`,`project_name`) VALUES ('http://hzling21.china.nsn-net.net:8080/',NULL,'jenkins',NULL,'TDLTE'),('http://hzling23.china.nsn-net.net:8080/',NULL,'jenkins',NULL,'TDLTE'),('http://ltecbwro.inside.nsn.com:8080/',NULL,'jenkins',NULL,'TDLTE'),('http://ltecbwro.inside.nsn.com:9000/',NULL,'jenkins',NULL,'TDLTE'),('http://ullteb40.emea.nsn-net.net:9093/',NULL,'jenkins',NULL,'TDLTE');

/** table "t_user" */
INSERT INTO `t_user` (`nsn_account`,`display_name`,`mail`,`nsn_id`) VALUES ('l58wang','Wang, Lei-Leo (Nokia - CN/Hangzhou)','lei-leo.wang@nokia.com','69015601');
INSERT INTO `t_user` (`nsn_account`,`display_name`,`mail`,`nsn_id`) VALUES ('w5cao','Cao, Wei-Tom (Nokia - CN/Hangzhou)','wei-tom.cao@nokia.com','62002738');
INSERT INTO `t_user` (`nsn_account`,`display_name`,`mail`,`nsn_id`) VALUES ('judwang','Wang, Juan-Judy (Nokia - CN/Hangzhou)','juan-judy.wang@nokia.com','67001560');
INSERT INTO `t_user` (`nsn_account`,`display_name`,`mail`,`nsn_id`) VALUES ('y75zhang','Zhang, Yang-Mark (Nokia - CN/Hangzhou)','yang-mark.zhang@nokia.com','67001511');

/** table "t_member" */
INSERT INTO `t_member` (`project_id`,`nsn_account`,`role_id`) VALUES (1,'l58wang',1);
INSERT INTO `t_member` (`project_id`,`nsn_account`,`role_id`) VALUES (1,'w5cao',1);
INSERT INTO `t_member` (`project_id`,`nsn_account`,`role_id`) VALUES (1,'judwang',1);
INSERT INTO `t_member` (`project_id`,`nsn_account`,`role_id`) VALUES (1,'y75zhang',1);

/** table "t_branch" */
INSERT INTO `t_branch` (`name`,`project_name`) VALUES ('FSMR3','TDLTE'),('AirScale','TDLTE'),('TL16A','TDLTE'),('TL16','TDLTE'),('TL15A','TDLTE'),('PSINT_FSMR3','TDLTE'),('PSINT_FSMR4','TDLTE'),('FZM','TDLTE'),('TLF15A','TDLTE'),('TLF16','TDLTE'),('TLF16A','TDLTE');

/**
 Data for table "t_job"
*/
INSERT INTO `t_job` (`job_name`,`threshold`,`watch`,`branch_name`,`address`,`project_name`) VALUES ('LTE_eNB_FSM3_TDD_trunk',8,b'1','FSMR3','http://hzling23.china.nsn-net.net:8080/','TDLTE'),('LTE_eNB_FSMr4_TDD_trunk',8,b'1','AirScale','http://hzling23.china.nsn-net.net:8080/','TDLTE'),('CB_TDD_TL16A',24,b'1','TL16A','http://hzling21.china.nsn-net.net:8080/','TDLTE'),('CB_TDD_TL16',24,b'1','TL16','http://hzling21.china.nsn-net.net:8080/','TDLTE'),('CB_TDD_TL15A',24,b'1','TL15A','http://hzling21.china.nsn-net.net:8080/','TDLTE'),('PS_INT1_CI_TDD',24,b'1','PSINT_FSMR3','http://ltecbwro.inside.nsn.com:8080/','TDLTE'),('PS_INT1_CI_TDD_FSMR4',24,b'1','PSINT_FSMR4','http://ltecbwro.inside.nsn.com:8080/','TDLTE'),('LTE_eNB_FZM_TDD_trunk',8,b'1','FZM','http://ullteb40.emea.nsn-net.net:9093/','TDLTE'),('CB_FZM_TDD_FL15A',24,b'1','TLF15A','http://ltecbwro.inside.nsn.com:9000/','TDLTE'),('CB_FZM_TDD_TLF16',24,b'1','TLF16','http://ltecbwro.inside.nsn.com:9000/','TDLTE'),('CB_FZM_TDD_TLF16A_Watcher',24,b'1','TLF16A','http://ltecbwro.inside.nsn.com:9000/','TDLTE');


