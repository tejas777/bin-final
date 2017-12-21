# Script Starts here SqoopJobs.sh ########

#!/bin/bash
# Title: Shell script to perform import and export between MySQL to HDFS and vice-versa using Sqoop 
# Author: Pavan Jaiswal 
#Note: This program can be made using shell script or Java. I have chosen shell script here. 



# Description: 
# Prerequisite is  
#    1. You must have MySql installed on your system. 
#    2. Keep the mysql connector jar into sqoop lib folder on your machine. I am using "mysql-connector-java-5.1.23-bin.jar"
#    2. Run hadoop and check its processes existence. My hadoop version is 2.6
#    3. In case you get jar related errors for sqoop, it means you are suppose to copy jar files from sqoop directory and lib directory to HDFS
#        HDFS path in your case can vary. So you must have to take care of that. In my case HDFS path is:
#        /usr/local/sqoop-1.4.5/lib/*.jar
#    4. Set the  permission to bash scropt file in which you will be putting this source code. use command: $ chmod 777 scriptfilename

# Initially I have created a table called 'studlog' with three columns. You can change the table schema as per your need. 
# Insert values into studlog table    
# Now you are in position to import table data and put it onto HDFS filesystem. In my case on HDFS directory is created by the name "studlog" which 
# contains partition files "/user/pavan/studlog/part-m-00000"
# Now before exporting back to mysql, I have created another empty table called "hdfsfiledata". You can change the table name. 
# After successful export, you can select data from table "hdfsfiledata" 
# You are done with this script. 
# For any queries you may approach me on pavan.jaiswal85@gmail.com 
#




################## Perform mysql operations ###############
mysql -uroot newdb << END_MYSQL

show tables;
drop table if exists studlog;
create table  studlog(id int, name varchar(20), book varchar(20));

desc studlog;

insert into studlog values(1,'John','Hadoop');
insert into studlog values(2,'Peter','Java');

select * from studlog;

END_MYSQL
echo "Done with mysql operations";


################# Import mysql table into HDFS #####################

echo $SQOOP_HOME
echo "Removing hdfs file if exists "

hadoop fs -rm -r /user/vinayak/studlog

sqoop import --username root --connect jdbc:mysql://localhost:3306/newdb --table studlog --m 1

hadoop fs -ls /user/vinayak/

hadoop fs -cat /user/vinayak/studlog/part-m-00000

echo "Mysql table imported successfully ";


################# Export file from HDFS to mysql table #####################
# for exporting file, create a empty table first. I have changed table name here
mysql -uroot newdb << END_MYSQL

drop table if exists hdfsfiledata;

create table hdfsfiledata (id int, name varchar(20), book varchar(20));

desc hdfsfiledata;

END_MYSQL

sqoop export --username root --connect jdbc:mysql://localhost:3306/newdb --table hdfsfiledata --m 1 --export-dir /user/vinayak/studlog/part-m-00000

echo "Export successful";

# Check the table contents

#echo "select * from hdfsfiledata;" | mysql -uroot newdb;
mysql -uroot newdb << END_MYSQL
select * from hdfsfiledata;
END_MYSQL

echo "Congratulations. Import Export done successfully";

# Script Starts here ########

# ***************************************** ###############

# To run this script use following commands

#[pavan@Pavan ~]$ chmod 777 SqoopJobs.sh
#[pavan@Pavan ~]$ sh SqoopJobs.sh
