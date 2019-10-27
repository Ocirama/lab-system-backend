Create database kuro_bandymu_sistema;
use kuro_bandymu_sistema;

CREATE TABLE  Order_Log ( 
id int PRIMARY KEY AUTO_INCREMENT,
protocol_Id varchar(50) unique ,
customer varchar(50),
test varchar(50),
sample_type varchar(50),
order_amount int,
data date
);

CREATE TABLE  Sample_Log ( 
id int PRIMARY KEY AUTO_INCREMENT,
sample_id varchar(50) unique,
sample_weight double,
order_id varchar(50) unique,
data date,
FOREIGN KEY (Order_ID) REFERENCES Order_Log (protocol_Id)
);


CREATE TABLE Tray_Log ( 
id int PRIMARY KEY AUTO_INCREMENT,
tray_id varchar(50) unique,
sample_id varchar(50) unique,
FOREIGN KEY (sample_id) REFERENCES Sample_Log (sample_id)
);

 CREATE TABLE  Reference_Tray_Log ( 
 id int PRIMARY KEY AUTO_INCREMENT,
 reference_tray_id varchar(50),
 reference_tray_weight_before double,
 reference_tray_weight_after double
 );
 

 CREATE TABLE  Total_Moisture_Log ( 
 id int PRIMARY KEY AUTO_INCREMENT,
 tray_id varchar(50) unique,
 tray_weight double,
 total_tray_weight_before double,
 total_tray_weight_after double,
 total_tray_weight_after_plus double,
 date date,
 FOREIGN KEY (tray_id) REFERENCES Tray_Log (tray_Id)
 );
 
 CREATE TABLE  General_Moisture_Log ( 
 id int PRIMARY KEY AUTO_INCREMENT,
 tray_id varchar(50) unique,
 general_tray_weight double,
 general_tray_weight_before double,
 general_tray_weight_after double,
 general_tray_weight_after_plus double,
 date date,
 FOREIGN KEY (tray_id) REFERENCES Tray_Log (tray_Id)
 );
 
 
 CREATE TABLE  Ash_Log ( 
 id int PRIMARY KEY AUTO_INCREMENT,
 tray_id varchar(50) unique,
 sample_id varchar(50) unique,
 ash_tray_weight double,
 ash_tray_weight_before double,
 ash_tray_weight_after double,
 date date,
 FOREIGN KEY (tray_id) REFERENCES Tray_Log (tray_Id)
 );
 
show tables;
select * from ash_log;
select * from general_moisture_log;
select * from order_log;
select * from reference_tray_log;
select * from sample_log;
select * from total_moisture_log;
select * from tray_log;

drop table ash_log;
drop table general_moisture_log;
drop table order_log;
drop table reference_tray_log;
drop table sample_log;
drop table total_moisture_log;
drop table tray_log;
