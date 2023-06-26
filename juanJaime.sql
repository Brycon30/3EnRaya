create database conexionJavaSQL

create table usuarios (
idUsuario int IDENTITY NOT NULL PRIMARY KEY,
nombreUsuario varchar(20) NOT NULL,
contraseña varchar(20) NOT NULL, 
telefono varchar(10) NOT NULL,
partidasGanadas int NOT NULL,
)

create table partidas (
idPartida int IDENTITY NOT NULL PRIMARY KEY,
usuarioO INT,
usuarioX INT,
usuarioGanador varchar(1),

foreign key (usuarioO) references usuarios(idUsuario),
foreign key (usuarioX) references usuarios(idUsuario)
)

alter trigger verificarUsuario
ON usuarios
FOR INSERT
as
begin

declare @usuarioNombre varchar(20)
select @usuarioNombre = nombreUsuario from inserted
IF EXISTS (select*from usuarios where nombreUsuario = @usuarioNombre)
begin
	RAISERROR('El usuario ya existe', 16,1)
	ROLLBACK TRANSACTION;
end

end

insert into usuarios values('tongoxd','hailgrasa','6971133066',10)
insert into usuarios values('bry555','enchiladas','6971094100',4)
insert into usuarios values('bry555','enchildas2','6971104201',5)

delete from usuarios where contraseña = 'enchildas2'

select*from usuarios