
ALTER PROCEDURE TransferirPorNumero(
    @CuentaOrigen VARCHAR(25), 
    @NumeroTelefono VARCHAR(25), 
    @SaldoTransferencia DECIMAL(12,2)
)
AS
BEGIN
    DECLARE @Intentos INT = 0;
    DECLARE @FechaHora DATETIME = GETDATE();
    DECLARE @TransTipo VARCHAR(3) = 'T';
    DECLARE @Dead INT = 1205;
    DECLARE @Saldo NUMERIC(12,2), @TransNumero INT, @IDCuentaDestino INT, @CuentaDestino Varchar(25)

    WHILE @Intentos < 3
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        BEGIN TRANSACTION;
        
        BEGIN TRY
            SELECT @Saldo = Saldo FROM Cuenta WHERE Cuenta = @CuentaOrigen;
            SELECT @TransNumero = ISNULL(MAX(idTransaccion), 0) + 1 FROM Transaccion;
            SELECT @IDCuentaDestino = u.idUsuario, @CuentaDestino = c.cuenta 
            FROM Usuario u 
            JOIN Cuenta c ON c.idUsuario = u.idUsuario
            WHERE u.telefono = @NumeroTelefono
            IF @SaldoTransferencia < 0
            BEGIN
                RAISERROR('El valor de la transferencia no puede ser negativo', 16, 1);
                RETURN;
            END
            IF EXISTS(SELECT 1 FROM Transaccion WHERE NumeroCuentaOrigen = @CuentaOrigen AND idTransaccion = @TransNumero)
            BEGIN
                IF @Saldo >= @SaldoTransferencia
                BEGIN
                    UPDATE Transaccion 
                    SET 
                        TipoTransaccion = @TransTipo, 
                        monto = @SaldoTransferencia, 
                        fechahora = @FechaHora, 
                        NumeroCuentaOrigen = @CuentaOrigen 
                    WHERE 
                        NumeroCuentaOrigen = @CuentaOrigen 
                        AND idTransaccion = @TransNumero;
                    
                    IF EXISTS(SELECT 1 FROM Usuario WHERE telefono = @NumeroTelefono AND idUsuario = @IDCuentaDestino)
                    BEGIN
                        UPDATE Cuenta 
                        SET Saldo = Saldo - @SaldoTransferencia 
                        WHERE Cuenta = @CuentaOrigen;
                       
                        UPDATE Cuenta 
                        SET Saldo = Saldo + @SaldoTransferencia 
                        WHERE Cuenta = @CuentaDestino;
                    END
                    ELSE
                    BEGIN
                        RAISERROR('La cuenta destino no existe', 16, 1);
                        RETURN;
                    END
                END
                ELSE
                BEGIN
                    RAISERROR('Saldo insuficiente en la cuenta origen', 16, 1);
                    RETURN;
                END
            END
            ELSE
            BEGIN
                IF @Saldo >= @SaldoTransferencia
                BEGIN
                    INSERT INTO Transaccion 
                    VALUES (@TransNumero, @TransTipo, @SaldoTransferencia, @FechaHora, @CuentaOrigen, @CuentaDestino);
					Set @TransNumero = @TransNumero + 1
					INSERT INTO Transaccion 
                    VALUES (@TransNumero, 'D', @SaldoTransferencia, @FechaHora, @CuentaDestino, '');
                    IF EXISTS(SELECT 1 FROM Cuenta WHERE Cuenta = @CuentaDestino AND idUsuario = @IDCuentaDestino)
                    BEGIN
                        UPDATE Cuenta 
                        SET Saldo = Saldo - @SaldoTransferencia 
                        WHERE Cuenta = @CuentaOrigen;
                        UPDATE Cuenta 
                        SET Saldo = Saldo + @SaldoTransferencia 
                        WHERE Cuenta = @CuentaDestino;
                    END
                    ELSE
                    BEGIN
                        RAISERROR('La cuenta destino no existe', 16, 1);
                        RETURN;
                    END
                END
                ELSE
                BEGIN
                    RAISERROR('Saldo insuficiente en la cuenta origen', 16, 1);
                    RETURN;
                END
            END
            COMMIT;
            BREAK;
        END TRY
        BEGIN CATCH
            IF ERROR_NUMBER() = @Dead
            BEGIN
                SET @Intentos = @Intentos + 1;
                WAITFOR DELAY '00:00:01';
                SELECT 'Intento numero: ', @Intentos;
                ROLLBACK;
            END
            ELSE
            BEGIN
                DECLARE @NumeroError INT = 50000 + ERROR_NUMBER();
                DECLARE @MesageError NVARCHAR(4000) = ERROR_MESSAGE();
                ROLLBACK;
                THROW @NumeroError, @MesageError, 1;
            END	
		end catch
	end
end
