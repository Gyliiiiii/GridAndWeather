create table if not exists coursetime
(
	ccid char(6),
	tid char(6)primary key,
	cnum varchar(20),
	cweeks varchar(20),
	cweek varchar(20),
	constraint fk_mid foreign key(ccid) references course(ccid)
);

create table if not exists firsttime
(
	fid char(6)primary key,
	ftime varchar(20),
);

create table if not exists course
(
	ccid char(6)primary key,
	coursename varchar(20),
	cteacher varchar(20),
	place varchar(50)
);