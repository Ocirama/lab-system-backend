Create database kuro_bandymu_sistema;
use kuro_bandymu_sistema;

CREATE TABLE  order_log (
id int PRIMARY KEY AUTO_INCREMENT,
protocol_Id varchar(50) not null,
customer varchar(50) not null,
test varchar(50)not null,
sample_type varchar(50) not null,
order_amount int not null,
date date not null
);

CREATE TABLE  sample_log (
id int PRIMARY KEY AUTO_INCREMENT,
sample_id varchar(50) not null,
sample_weight double,
order_id  int not null,
date date,
FOREIGN KEY (order_id) REFERENCES order_log (id)
);


CREATE TABLE tray_log (
id int PRIMARY KEY AUTO_INCREMENT,
tray_id varchar(50) not null,
sample_id int not null,
date date,
FOREIGN KEY (sample_id) REFERENCES sample_log (id)
);

 CREATE TABLE  reference_tray_log (
 id int PRIMARY KEY AUTO_INCREMENT,
 reference_tray_id varchar(50) not null,
 reference_tray_weight_before double not null,
 reference_tray_weight_after double not null
 date date
 );
 

 CREATE TABLE  total_moisture_log (
 id int PRIMARY KEY AUTO_INCREMENT,
 sample_id int not null,
 tray_id varchar(50) not null,
 tray_weight double not null,
 total_tray_weight_before double not null,
 total_tray_weight_after double,
 total_tray_weight_after_plus double,
 date date,
FOREIGN KEY (sample_id) REFERENCES sample_log (id)
 );
 
 CREATE TABLE  general_moisture_log (
 id int PRIMARY KEY AUTO_INCREMENT,
 tray_id int  not null,
 general_tray_weight double not null,
 general_tray_weight_before double not null,
 general_tray_weight_after double not null,
 general_tray_weight_after_plus double not null,
 date date,
 FOREIGN KEY (tray_id) REFERENCES tray_log (id)
 );
 
 
 CREATE TABLE  ash_log (
 id int PRIMARY KEY AUTO_INCREMENT,
 tray_id int not null,
 ash_tray_weight double not null,
 ash_tray_weight_before double not null,
 ash_tray_weight_after double not null,
 date date,
 FOREIGN KEY (tray_id) REFERENCES tray_log (id)
 );

