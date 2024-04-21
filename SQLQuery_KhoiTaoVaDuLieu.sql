USE master;
GO

IF NOT EXISTS (
    SELECT name
    FROM sys.databases
    WHERE name = N'staff_management'
)
CREATE DATABASE staff_management;
GO

USE staff_management;
GO

-- Table structure for table `admin`
IF OBJECT_ID('admin', 'U') IS NOT NULL
DROP TABLE admin;
GO

CREATE TABLE admin (
  id INT NOT NULL PRIMARY KEY,
  name NVARCHAR(255) NOT NULL,
  username NVARCHAR(100) NOT NULL,
  password NVARCHAR(255) NOT NULL,
  date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
GO

-- Table structure for table `staff`
IF OBJECT_ID('staff', 'U') IS NOT NULL
DROP TABLE staff;
GO

CREATE TABLE staff (
  id INT NOT NULL PRIMARY KEY,
  name NVARCHAR(255) NOT NULL,
  birthday DATE NOT NULL,
  salary INT NOT NULL,
  gender NVARCHAR(10) NOT NULL,
  is_married NVARCHAR(3) NOT NULL,
  date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
GO

-- Insert data into table `admin`
INSERT INTO admin VALUES 
(1,'Code with me <3','admin','21232f297a57a5a743894a0e4a801fc3','2018-11-12 17:18:48','2018-11-13 01:43:56');

-- Insert data into table `staff`
INSERT INTO staff VALUES 
(1,'Lê Văn Nam','1991-01-01',60000,'male','yes','2018-11-13 22:26:14','2018-11-13 15:26:14'),
(2,'Hoàng Văn Anh','1990-02-13',30000,'male','no','2018-11-13 12:05:47','2018-11-13 05:05:47'),
(3,'Nguyễn Huy Hoàng','1989-01-12',65000,'male','no','2018-11-13 22:26:36','2018-11-13 15:26:36'),
(4,'Lê Thị Lan','1995-06-18',3000,'female','no','2018-11-13 22:27:04','2018-11-13 15:27:04'),
(5,'Hoàng Văn Linh','1987-12-01',13000,'male','yes','2018-11-13 22:27:32','2018-11-13 15:27:32'),
(6,'Lê Hoàng Huy','1997-10-06',4000,'male','no','2018-11-13 22:27:59','2018-11-13 15:27:59'),
(7,'Hà Văn Hùng','1980-09-12',1000,'female','no','2018-11-13 22:28:25','2018-11-13 15:28:25'),
(8,'Nguyễn Văn B','1995-11-10',3000,'male','yes','2018-11-13 22:28:53','2018-11-13 15:28:53');
