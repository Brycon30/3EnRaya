--SE CREA LA BASE DE DATOS
create database tresenraya

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
usuarioGanador varchar(1),
foreign key (jugadorO) references jugadores(nombre),
foreign key (jugadorX) references jugadores(nombre)
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

--SE CREA TRIGGER PARA AGREGAR A LA BITACORA LOS INSERTS LOS NUEVOS JUGADORES
alter trigger insertJugadores
on jugadores
for insert
as 
begin
--declaramos una var que nos servira para conectar el registro nuevo
declare @reg varchar(500)
--concatenamos el registro de la tabla inserted en una sola variable
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
--insertamos en la tabla bitacora el registro de nuevo y los datos de control
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),null,@reg)
end

--SE CREA EL TRIGGER PARA AGREGAR A LA BITACORA LOS DELETES DE LOS JUGADORES
alter trigger deleteJugadores
on jugadores
for DELETE
as 
begin
--declaramos una var que nos servira para conectar el registro nuevo
declare @reg varchar(500)
--concatenamos el registro de la tabla deleted en una sola variable
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
--insertamos en la tabla bitacora el registro de nuevo y los datos de control
insert into bitacoraJugadores values(
GETDATE(),SYSTEM_USER,APP_NAME(),HOST_NAME(),@reg,null)
end

create trigger updateJugadores
on jugadores
for UPDATE
as 
begin
--declaramos una var que nos servira para conectar el registro nuevo
declare @reg varchar(500)
declare @regNuevo varchar(500)
--concatenamos el registro de la tabla deleted en una sola variable
select @reg=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from deleted
select @regNuevo=concat(nombre,'|',contraseña,'|',telefono,'|',partidasGanadas) from inserted
--insertamos en la tabla bitacora el registro de nuevo y los datos de control
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

exec ganarPartida ''

--SE CREA PROCEDIMIENTO ALMACENADO QUE MUESTRA EL LEADERBOARD
create procedure mostrarLeaderboard
as
begin
select nombre, partidasGanadas from jugadores
Order by partidasGanadas DESC;
end

insert into partidas values(clienteId,Remitente,usuarioGanador)

create procedure winrate
@nombre varchar(50)
as
begin
declare @partidasTotales int, @partidasGanadas int, @winrate int
select @partidasTotales = (select COUNT(*) as totales from partidas where jugadorO = @nombre OR jugadorX = @nombre)
select @partidasGanadas = (select COUNT(*) as ganadas from partidas where usuarioGanador = @nombre)

select @winrate = (@partidasGanadas / @partidasTotales)*100

end

exec winrate 'Juan'

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

delete from jugadores where nombre = 'a'

update jugadores
set nombre = 'Bry55'
where nombre = 'Bryo'

select*from jugadores
select*from bitacoraJugadores
