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

--SE CREA EL PROCEDIMIENTO ALMACENADO QUE AUMENTA LAS PARTIDAS GANADAS Y SE LE ENVÍA DE VALOR EL NOMBRE JUGADOR GANADOR
create procedure ganarPartida
@nombre varchar(20)
as
begin
update jugadores
set partidasGanadas = partidasGanadas+1
where nombre = @nombre
end
