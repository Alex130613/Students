CREATE DATABASE ��������� 
 ON PRIMARY                   
   ( NAME = ���������_data,  
     FILENAME = 'D:\������\������\���������_data.mdf',
     SIZE = 5MB, 
     MAXSIZE = 75MB,
     FILEGROWTH = 3MB ),
 FILEGROUP Secondary
   ( NAME = ���������2_data,
     FILENAME = 'D:\������\������\���������_data2.ndf',
     SIZE = 3MB, 
     MAXSIZE = 50MB,
     FILEGROWTH = 15% ),
   ( NAME = ���������3_data,
     FILENAME = 'D:\������\������\���������_data3.ndf',
     SIZE = 4MB, 
     FILEGROWTH = 4MB )
 LOG ON
   ( NAME = ���������_log,
     FILENAME = 'D:\������\������\���������_log.ldf',
     SIZE = 1MB,
     MAXSIZE = 10MB,
     FILEGROWTH = 20% ),
   ( NAME = ���������2_log,
     FILENAME = 'D:\������\������\���������_log2.ldf',
     SIZE = 512KB,
     MAXSIZE = 15MB,
     FILEGROWTH = 10% )
 GO  

 use ���������
 go

  CREATE TABLE ������������ (	
   �����	VARCHAR(20) PRIMARY KEY,
   ���		VARCHAR(40)  NULL,
   ������		VARCHAR(20)  NOT NULL,
   ����		VARCHAR(20)  NOT NULL CHECK (���� IN ('�������������','�������������','���������','�������'))
   )

CREATE TABLE ��������� (	
	 ������ varchar(10)  PRIMARY KEY
)
   CREATE TABLE ������� (	
   ID_��������	varchar(10)  PRIMARY KEY,
   �������		VARCHAR(30) NOT NULL,
	������ varchar(10) NOT NULL FOREIGN KEY REFERENCES ���������(������),
   �����	VARCHAR(20)  NOT NULL FOREIGN KEY REFERENCES ������������(�����)
	 )

     CREATE TABLE ��������� (	
   ID_���������	INT  PRIMARY KEY,
   ������������	VARCHAR(10) NOT NULL,
   ��������������	MONEY  NOT NULL 
   )

CREATE TABLE ������� (	
   ID_��������	varchar(10)  PRIMARY KEY,
   ���	VARCHAR(40)  NOT NULL,
    ���������� float NULL CHECK (����������>=0 AND ����������<=10),
	������ varchar(10) NOT NULL FOREIGN KEY REFERENCES ���������(������),
    ID_��������� INT NULL FOREIGN KEY REFERENCES ���������(ID_���������),
	��������������� MONEY NULL
)

     CREATE TABLE ������ (	
	 ID_������ varchar(10)  PRIMARY KEY,
   ID_��������	varchar(10) not null FOREIGN KEY REFERENCES ������� (ID_��������),
   ID_��������		varchar(10)  NOT NULL FOREIGN KEY REFERENCES �������(ID_��������),
   ������ INT NOT NULL CHECK (������>=0 AND ������<=10)
)

 GO
 
 INSERT INTO ������������(�����, ������, ����)
 VALUES ('Admin00', '00000000', '�������������')
 GO