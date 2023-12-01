CREATE DATABASE Стипендия 
 ON PRIMARY                   
   ( NAME = Стипендия_data,  
     FILENAME = 'D:\ПрогСП\Курсач\Стипендия_data.mdf',
     SIZE = 5MB, 
     MAXSIZE = 75MB,
     FILEGROWTH = 3MB ),
 FILEGROUP Secondary
   ( NAME = Стипендия2_data,
     FILENAME = 'D:\ПрогСП\Курсач\Стипендия_data2.ndf',
     SIZE = 3MB, 
     MAXSIZE = 50MB,
     FILEGROWTH = 15% ),
   ( NAME = Стипендия3_data,
     FILENAME = 'D:\ПрогСП\Курсач\Стипендия_data3.ndf',
     SIZE = 4MB, 
     FILEGROWTH = 4MB )
 LOG ON
   ( NAME = Стипендия_log,
     FILENAME = 'D:\ПрогСП\Курсач\Стипендия_log.ldf',
     SIZE = 1MB,
     MAXSIZE = 10MB,
     FILEGROWTH = 20% ),
   ( NAME = Стипендия2_log,
     FILENAME = 'D:\ПрогСП\Курсач\Стипендия_log2.ldf',
     SIZE = 512KB,
     MAXSIZE = 15MB,
     FILEGROWTH = 10% )
 GO  

 use Стипендия
 go

  CREATE TABLE Пользователь (	
   Логин	VARCHAR(20) PRIMARY KEY,
   ФИО		VARCHAR(40)  NULL,
   Пароль		VARCHAR(20)  NOT NULL,
   Роль		VARCHAR(20)  NOT NULL CHECK (Роль IN ('Администратор','Преподаватель','Бухгалтер','Деканат'))
   )

CREATE TABLE ВсеГруппы (	
	 Группа varchar(10)  PRIMARY KEY
)
   CREATE TABLE Предмет (	
   ID_Предмета	varchar(10)  PRIMARY KEY,
   Предмет		VARCHAR(30) NOT NULL,
	Группа varchar(10) NOT NULL FOREIGN KEY REFERENCES ВсеГруппы(Группа),
   Логин	VARCHAR(20)  NOT NULL FOREIGN KEY REFERENCES Пользователь(Логин)
	 )

     CREATE TABLE Стипендия (	
   ID_Стипендии	INT  PRIMARY KEY,
   ВидСтипендии	VARCHAR(10) NOT NULL,
   РазмерСтипедии	MONEY  NOT NULL 
   )

CREATE TABLE Студент (	
   ID_Студента	varchar(10)  PRIMARY KEY,
   ФИО	VARCHAR(40)  NOT NULL,
    СреднийБал float NULL CHECK (СреднийБал>=0 AND СреднийБал<=10),
	Группа varchar(10) NOT NULL FOREIGN KEY REFERENCES ВсеГруппы(Группа),
    ID_Стипендии INT NULL FOREIGN KEY REFERENCES Стипендия(ID_Стипендии),
	РазмерСтипендии MONEY NULL
)

     CREATE TABLE Оценки (	
	 ID_Оценки varchar(10)  PRIMARY KEY,
   ID_Студента	varchar(10) not null FOREIGN KEY REFERENCES Студент (ID_Студента),
   ID_Предмета		varchar(10)  NOT NULL FOREIGN KEY REFERENCES Предмет(ID_Предмета),
   Оценка INT NOT NULL CHECK (Оценка>=0 AND Оценка<=10)
)

 GO
 
 INSERT INTO Пользователь(Логин, Пароль, Роль)
 VALUES ('Admin00', '00000000', 'Администратор')
 GO