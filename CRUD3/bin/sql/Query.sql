--SE CREA LA BASE DE DATOS
create database tresenraya

--INTENTANDO HACER COMMIT DESDE SQL
--INTENTANDO HACER PULL DESDE GIT A SQL
--SE CREA LA TABLA DE USUARIOS/JUGADORES EN ESTE PUEDES CAMBIAR EL NOMBRE A LO QUE QUIERASXD
create table jugadores (
nombre varchar(50) NOT NULL primary key,
contraseña varchar(50) NOT NULL, 
telefono varchar(10) NOT NULL,
partidasGanadas int NOT NULL,
)

--SE CREA LA TABLA PARTIDAS
create table partidas (
idPartida int IDENTITY NOT NULL PRIMARY KEY,
jugadorO varchar(50),
jugadorX varchar(50),
usuarioGanador varchar(50),
foreign key (jugadorO) references jugadores(nombre),
foreign key (jugadorX) references jugadores(nombre)
)

--SE CREA LA TABLA DE PREGUNTAS DE SEGURIDAD
create table preguntasSeguridad (
idPregunta int IDENTITY NOT NULL PRIMARY KEY,
usuario varchar(50),
pregunta varchar(128),
respuesta varchar(128),
foreign key (usuario) referenes jugadores(nombre)
)

--SE CREA LA BITACORA DE USUARIOS
create table bitacoraJugadores (
fecha datetime primary key,
usuario varchar(50),
app varchar(200),
host varchar(50),
registro_anterior varchar(500),
registro_nuevo varchar(500)
)

create table bitacoraPartidas (
fecha datetime primary key,
usuario varchar(50),
app varchar(200),
host varchar(50),
registro_anterior varchar(500),
registro_nuevo varchar(500)
)

create table bitacoraPreguntas (
fecha datetime primary key,
usuario varchar(200),
app varchar(200),
host varchar(50),
registro_anterior varchar(500),
registro_nuevo varchar(500)
)

--SE CREA TRIGGER PARA AGREGAR A LA BITACORA LOS INSERT DE LAS PARTIDAS
create trigger insertPartidas
on partidas
for INSERT
as 
begin
declare @reg varchar(500)
declare @regNuevo varchar(500)
select @regNuevo=concat(idPartida,'|',jugadorO,'|',jugadorX,'|',usuarioGanador) from inserted
--select @regNuevo=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
insert into bitacoraPartidas values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,@regNuevo)
end

--SE CREA TRIGGER PARA AGREGAR A LA BITACORA LOS DELETE DE LAS PARTIDAS
create trigger deletePartidas
on partidas
for INSERT
as 
begin
declare @reg varchar(500)
declare @regNuevo varchar(500)
select @reg=concat(idPartida,'|',jugadorO,'|',jugadorX,'|',usuarioGanador) from deleted
insert into bitacoraPartidas values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,@regNuevo)
end

--SE CREA TRIGGER PARA AGREGAR A LA BITACORA LOS UPDATE DE LAS PARTIDAS
create trigger updatePartidas
on partidas
for INSERT
as 
begin
declare @reg varchar(500)
declare @regNuevo varchar(500)
select @reg=concat(idPartida,'|',jugadorO,'|',jugadorX,'|',usuarioGanador) from inserted
select @regNuevo=concat(idPartida,'|',jugadorO,'|',jugadorX,'|',usuarioGanador) from inserted
insert into bitacoraPartidas values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,@regNuevo)
end

--SE CREA TRIGGER PARA AGREGAR A LA BITACORA LOS INSERTS LOS NUEVOS JUGADORES
create trigger insertJugadores
on jugadores
for insert
as 
begin
declare @reg varchar(500)
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),null,@reg)
end

--SE CREA EL TRIGGER PARA AGREGAR A LA BITACORA LOS DELETES DE LOS JUGADORES
create trigger deleteJugadores
on jugadores
for DELETE
as 
begin
declare @reg varchar(500)
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,null)
end

create trigger updateJugadores
on jugadores
for UPDATE
as 
begin
declare @reg varchar(500)
declare @regNuevo varchar(500)
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
select @regNuevo=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,@regNuevo)
end

--SE CREA EL TRIGGER PARA VERIFICAR QUE EL USUARIO NO EXISTA
create trigger verificarUsuario
ON jugadores
INSTEAD OF INSERT
as
begin
IF EXISTS (select 1 from jugadores INNER JOIN inserted on jugadores.nombre = inserted.nombre)
begin
	RAISERROR('El usuario ya existe', 16,1)
	ROLLBACK TRANSACTION;
end
ELSE
BEGIN
INSERT INTO jugadores (nombre,contraseña,telefono,partidasGanadas) select nombre, contraseña, telefono, partidasGanadas from inserted
END
end

--SE CREA EL TRIGGER QUE EVITA QUE SE CAMBIE EL NOMBRE DE UN JUGADOR
create trigger verificarUpdateUsuario
ON jugadores
for update
as
begin
declare @nombreDel varchar(50), @nombreIns varchar(50)
select @nombreDel = nombre from deleted
select @nombreIns = nombre from inserted
if (@nombreDel <> @nombreIns)
begin
rollback
raiserror('No se puede actualizar el nombre',16,1)
return;
end
end

--SE CREA EL PROCEDIMIENTO ALMACENADO QUE AUMENTA LAS PARTIDAS GANADAS Y SE LE ENVÍA DE VALOR EL NOMBRE JUGADOR GANADOR
create procedure ganarPartida
@nombre varchar(20)
as
begin
update jugadores
set partidasGanadas = partidasGanadas+1
where nombre = @nombre
end

--SE CREA PROCEDIMIENTO ALMACENADO QUE MUESTRA EL LEADERBOARD
create procedure mostrarLeaderboard
as
begin
select nombre, partidasGanadas from jugadores
Order by partidasGanadas DESC;
end

create procedure winrate
@nombre varchar(50)
as
begin
declare @partidasTotales int, @partidasGanadas int, @winrate int
select @partidasTotales = (select COUNT(*) as totales from partidas where jugadorO = @nombre OR jugadorX = @nombre)
select @partidasGanadas = (select COUNT(*) as ganadas from partidas where usuarioGanador = @nombre)

select @winrate = (@partidasGanadas / @partidasTotales)*100

end

--SE CREA EL PROCESO ALMACENADO QUE REGISTRA LA PARTIDAS AUTOMATICAMENTE
create procedure crearPartida
@jugador0 varchar(50),
@jugadorX varchar(50),
@jugadorGanador varchar(50)
as begin
insert into partidas values(@jugador0,@jugadorX,@jugadorGanador)
end

--SE CREA PROCESO QUE ENCUENTRA LAS PARTIDAS JUGADAS POR UN JUGADOR ESPECIFICO
create procedure partidasTotales
@nombre varchar(50)
as begin
declare @partidasTotales int
select @partidasTotales = (select COUNT(*) as totales from partidas where jugadorO = @nombre or jugadorX = @nombre)

select @partidasTotales as totales
end

--INSERTS DE PRUEBA
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Juan', '123456', '1234567890', 3)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('María', 'abcdef', '9876543210', 5)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Pedro', 'qwerty', '5555555555', 1)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Ana', 'password', '6666666666', 2)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Luis', 'abc123', '1111111111', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('papu', 'abc123', '1111111111', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('mamu', 'abc123', '1111111111', 0)

delete from jugadores where nombre = 'a'

update jugadores
set nombre = 'Bry55'
where nombre = 'Bryo'

exec partidasTotales 'Bry555'

select*from jugadores
select*from partidas
select*from bitacoraJugadores
select*from bitacoraPartidas
