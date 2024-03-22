
ALTER PROCEDURE ReponerDineroCajero(
    @User VARCHAR(25),
    @PIN INT,
    @BilletesDe100 INT,
    @BilletesDe50 INT
)
AS
BEGIN
    DECLARE @Intentos INT = 0;
    DECLARE @FechaHora DATETIME = GETDATE();
    DECLARE @Dead INT = 1205;
    DECLARE @SaldoActual INT, @Billetes100Actual INT, @Billetes50Actual INT;
    DECLARE @Admin VARCHAR(25), @ContraAdmin INT, @ROL VARCHAR(25), @IDAdmin INT, @IdRetri INT, @Total INT = 0;
    
    IF @BilletesDe100 < 0 OR @BilletesDe50 < 0
    BEGIN
        RAISERROR('Solo se pueden aceptar billetes de 50 y 100', 16, 1);
        RETURN;
    END
    
    WHILE @Intentos < 3
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        BEGIN TRANSACTION;
        
        BEGIN TRY
            SELECT @ROl = rol, @ContraAdmin = pin, @IDAdmin = idUsuario FROM Usuario WHERE nombreUsuario = @User;
            --SELECT @IdRetri = MAX(id) + 1 FROM RegistroCajero;
            
            SELECT 
                @SaldoActual = cantidad, 
                @Billetes100Actual = billetesDe100, 
                @Billetes50Actual = billetesDe50 
            FROM Cajero 
            WHERE id = 1;
            
            IF @ROL = 'ADMIN' AND @PIN = @ContraAdmin
            BEGIN
                IF EXISTS(SELECT 1 FROM RegistroCajero WHERE administradorId = @IDAdmin AND @IdRetri = id)	
                BEGIN
                    SET @Total = (@BilletesDe100 * 100) + (@BilletesDe50 * 50);
                    UPDATE RegistroCajero 
                    SET 
                        fechahora = @FechaHora, 
                        billetesDe100 = @BilletesDe100, 
                        billetesDe50 = @BilletesDe50, 
                        total = @Total, 
                        administradorId = @IDAdmin,
						accion = 'A'
                    WHERE 
                        id = @IdRetri;

                    SET @Total = @Total + @SaldoActual;
                    SET @BilletesDe100 = @BilletesDe100 + @Billetes100Actual;
                    SET @BilletesDe50 = @BilletesDe50 + @Billetes50Actual;

                    UPDATE Cajero 
                    SET 
                        cantidad = @Total, 
                        billetesDe100 = @BilletesDe100, 
                        billetesDe50 = @BilletesDe50, 
                        fecha = @FechaHora
                    WHERE id = 1;
                END
                ELSE
                BEGIN 
                    SET @Total = (@BilletesDe100 * 100) + (@BilletesDe50 * 50);
                    INSERT INTO RegistroCajero VALUES (@FechaHora, @BilletesDe100, @BilletesDe50, @Total, @IDAdmin, 'A');
                    SET @Total = @Total + @SaldoActual;
                    SET @BilletesDe100 = @BilletesDe100 + @Billetes100Actual;
                    SET @BilletesDe50 = @BilletesDe50 + @Billetes50Actual;

                    UPDATE Cajero 
                    SET 
                        cantidad = @Total, 
                        billetesDe100 = @BilletesDe100, 
                        billetesDe50 = @BilletesDe50, 
                        fecha = @FechaHora
                    WHERE id = 1;
                END
            END
            ELSE
            BEGIN
                RAISERROR('No cuentas con permisos necesarios', 16, 1);
                RETURN;
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
        END CATCH;
    END
END;
