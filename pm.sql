drop database pm;
CREATE DATABASE pm;
use pm;
create table user(
user_id  MEDIUMINT PRIMARY KEY AUTO_INCREMENT,
username varchar(255) not null,
password varchar(255) not null,
work_number varchar(255),
role TINYINT(1) not null,
introduction varchar(1000)
);

create table activity(
activity_id MEDIUMINT PRIMARY KEY AUTO_INCREMENT,
activity_name varchar(255) not null,
introduction varchar(2000) not null,
type varchar(255) not null,
picture varchar(255) not null,
activity_start_time dateTime not null,
activity_end_time dateTime not null,
capacity MEDIUMINT not null,
sign_up_start_time dateTime not null,
sign_up_end_time dateTime not null,
create_time dateTime not null,
launch_time dateTime
);

create table venue(
venue_id MEDIUMINT PRIMARY KEY AUTO_INCREMENT,
campus varchar(255) not null,
venue_name varchar(255) not null
);

create table launchActivity(
user_id MEDIUMINT not null,
activity_id MEDIUMINT not null,
PRIMARY KEY(user_id, activity_id),
FOREIGN KEY(user_id) REFERENCES user(user_id),
FOREIGN KEY(activity_id) REFERENCES activity(activity_id)
);

create table participate(
user_id MEDIUMINT not null,
activity_id MEDIUMINT not null,
sign_up_time dateTime not null,
present TINYINT DEFAULT 0,
score TINYINT,
comment varchar(1000),
picture varchar(255),
PRIMARY KEY(user_id, activity_id),
FOREIGN KEY(user_id) REFERENCES user(user_id),
FOREIGN KEY(activity_id) REFERENCES activity(activity_id)
);

create table activityVenue(
activity_id MEDIUMINT not null,
venue_id MEDIUMINT not null,
PRIMARY KEY(activity_id, venue_id),
FOREIGN KEY(venue_id) REFERENCES venue(venue_id),
FOREIGN KEY(activity_id) REFERENCES activity(activity_id)
);