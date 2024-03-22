
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY,
    nombreUsuario VARCHAR(50) UNIQUE NOT NULL,
	telefono varchar(8),
    pin VARCHAR(10) NOT NULL,
    rol VARCHAR(10) NOT NULL
);
CREATE TABLE Cuenta (
    cuenta VARCHAR(25) UNIQUE NOT NULL ,
    saldo DECIMAL(12, 2) NOT NULL,
	idUsuario int NOT NULl
	FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Transaccion (
    idTransaccion INT PRIMARY KEY,
	TipoTransaccion VARCHAR(1)  NOT NULL,
    monto DECIMAL(12, 2) NOT NULL,
    fechahora DATETIME NOT NULL,
    NumeroCuentaOrigen INT NOT NULL,
	NumeroCuentaDestino INT
);

CREATE TABLE Cajero (
    id INT PRIMARY KEY,
    cantidad INT NOT NULL,
	billetesDe100 int not null,
	billetesDe50 int not null,
	fecha DATE
);


CREATE TABLE RegistroCajero (
    id INT PRIMARY KEY IDENTITY,
    fechahora DATETIME NOT NULL, 
	billetesDe100 int,
	billetesDe50 int,
    total DECIMAL(12, 2) NOT NULL,
    administradorId INT,
	accion varchar(2) not NULL
);



