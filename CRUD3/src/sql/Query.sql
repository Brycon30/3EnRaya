--SE CREA LA BASE DE DATOS
create database tresenraya

--INTENTANDO HACER COMMIT DESDE SQL
--INTENTANDO HACER PULL DESDE GIT A SQL
--SE CREA LA TABLA DE USUARIOS/JUGADORES EN ESTE PUEDES CAMBIAR EL NOMBRE A LO QUE QUIERASXD
create table jugadores (
idJugador int identity primary key NOT NULL,
nombre varchar(50) NOT NULL,
contraseña varchar(50) NOT NULL, 
telefono varchar(10) NOT NULL,
partidasGanadas int NOT NULL,
)

--SE CREA LA TABLA PARTIDAS
create table partidas (
idPartida int IDENTITY NOT NULL PRIMARY KEY,
jugadorO int,
jugadorX int,
usuarioGanador int,
foreign key (jugadorO) references jugadores(idJugador),
foreign key (jugadorX) references jugadores(idJugador)
)

--SE CREA LA TABLA DE PREGUNTAS DE SEGURIDAD
create table preguntasSeguridad (
idPregunta int IDENTITY NOT NULL PRIMARY KEY,
usuario int,
pregunta varchar(128),
respuesta varchar(128),
foreign key (usuario) references jugadores(idJugador)
)

--SE CREA LA BITACORA DE USUARIOS
create table bitacoraJugadores (
fecha datetime,
usuario varchar(50),
app varchar(200),
host varchar(50),
registro_anterior varchar(500),
registro_nuevo varchar(500)
)

create table bitacoraPartidas (
fecha datetime,
usuario varchar(50),
app varchar(200),
host varchar(50),
registro_anterior varchar(500),
registro_nuevo varchar(500)
)

create table bitacoraPreguntas (
fecha datetime,
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
for DELETE
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
for UPDATE
as 
begin
declare @reg varchar(500)
declare @regNuevo varchar(500)
select @reg=concat(idPartida,'|',jugadorO,'|',jugadorX,'|',usuarioGanador) from deleted
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
select @reg=concat(idJugador,'|',nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
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
select @reg=concat(idJugador,'|',nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
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
select @reg=concat(idJugador,'|',nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
select @regNuevo=concat(idJugador,'|',nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,@regNuevo)
end

create trigger insPregunta
on preguntasSeguridad
instead of insert
as
begin
declare @qIns varchar(128), @aIns varchar(128), @idIns int
select @qIns = pregunta from inserted
select @aIns = respuesta from inserted
select @idIns = usuario from inserted
IF EXISTS (select 1 from preguntasSeguridad INNER JOIN inserted on preguntasSeguridad.usuario = inserted.usuario)
begin
	update preguntasSeguridad
	set pregunta = @qIns
	where usuario = @idIns

	update preguntasSeguridad
	set respuesta = @aIns
	where usuario = @idIns
end
ELSE
BEGIN
INSERT INTO preguntasSeguridad(usuario,pregunta,respuesta) select usuario, pregunta, respuesta from inserted
END
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
alter trigger verificarUpdateUsuario
ON jugadores
instead of update
as
begin
declare @nombreDel varchar(50), @nombreIns varchar(50), @id int, @passwordIns varchar(50), @telefonoIns varchar(10)
select @nombreDel = nombre from deleted
select @nombreIns = nombre from inserted
select @passwordIns = contraseña from inserted
select @telefonoIns = telefono from inserted
select @id = idJugador from inserted
IF UPDATE(nombre)--EN CASO DE QUE SE QUIERA ACTUALIZAR NOMBRE
	IF EXISTS (select 1 from jugadores where nombre = @nombreIns)
		begin
		print 'se encontró usuario que se llama igual'
		rollback
		raiserror('No se puede actualizar el nombre',16,1)
		return;
		end
	else
		begin
		update jugadores
		set nombre = @nombreIns
		where nombre = @nombreDel
	end
else if UPDATE(partidasGanadas)--EN CASO DE QUE SE QUIERAN ACTUALIZAR LAS PARTIDAS GANADAS
	begin
	update jugadores
	set partidasGanadas = partidasGanadas+1
	where nombre = @nombreIns
	end
else if UPDATE(contraseña)-- EN CASO DE QUE SE QUIERA ACTUALIZAR LA CONTRASEÑA
	begin
	update jugadores
	set contraseña = @passwordIns
	where idJugador = @id
	end
else if UPDATE(telefono)-- EN CASO DE QUE SE QUIERA ACTUALIZAR EL NUMERO DE TELEFONO
	begin
	update jugadores
	set telefono = @telefonoIns
	where idJugador = @id
	end
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

declare @id int

select @id = (select idJugador from jugadores where nombre = @nombre)

declare @partidasTotales int, @partidasGanadas int, @winrate DECIMAL(5,2)
select @partidasTotales = (select COUNT(*) as totales from partidas where jugadorO = @id OR jugadorX = @id)
select @partidasGanadas = (select COUNT(*) as ganadas from partidas where usuarioGanador = @id)
IF @partidasTotales = 0
	SET @winrate = 0
ELSE
	select @winrate = (CONVERT(DECIMAL(5, 2), @partidasGanadas) / @partidasTotales) * 100

select @winrate as winrate

end

--SE CREA EL PROCESO ALMACENADO QUE REGISTRA LA PARTIDAS AUTOMATICAMENTE Y AUMENTA LAS PARTIDAS GANADAS A LOS JUGADORES
create procedure crearPartida
@jugador0 varchar(50),
@jugadorX varchar(50),
@jugadorGanador varchar(50)
as begin

declare @idO int, @idX int, @idGanador int
select @idO = (select idJugador from jugadores where nombre = @jugador0)
select @idX = (select idJugador from jugadores where nombre = @jugadorX)
select @idGanador = (select idJugador from jugadores where nombre = @jugadorGanador)

insert into partidas values(@idO,@idX,@idGanador)

update jugadores
set partidasGanadas = partidasGanadas+1
where idJugador = @idGanador

end

--SE CREA PROCESO QUE ENCUENTRA LAS PARTIDAS JUGADAS POR UN JUGADOR ESPECIFICO
create procedure partidasTotales
@nombre varchar(50)
as
begin
declare @id int
select @id = (select idJugador from jugadores where nombre = @nombre)
select count(*) as totales from partidas where jugadorO = @id or jugadorX = @id
end

--INSERTS DE PRUEBA
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Bryo', '123', '1234567890', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('papu', '123', '9876543210', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Pedro', 'qwerty', '5555555555', 1)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Ana', 'password', '6666666666', 2)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('Luis', 'abc123', '1111111111', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('papu', 'abc123', '1111111111', 0)
INSERT INTO jugadores (nombre, contraseña, telefono, partidasGanadas)
VALUES ('tongo', 'abc123', '1111111111', 0)

select*from partidas
select*from jugadores

exec crearPartida 'tongo','papu','tongo'


update jugadores
set contraseña = 'pruebaupdate'
where nombre = 'tongo'

exec winrate 'papu'

delete from preguntasSeguridad where usuario = 7

select idJugador from jugadores where nombre = 'Bryo'

insert into partidas

select * from preguntasSeguridad