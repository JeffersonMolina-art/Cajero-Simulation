ALTER PROCEDURE ReporteComprobacionSaldos
(
    @NumeroCuenta VARCHAR(25)
)
AS
BEGIN
    DECLARE @Intentos INT = 0;
    DECLARE @FechaHora DATETIME = GETDATE();
    DECLARE @Dead INT = 1205;
    
    WHILE @Intentos < 3
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
        BEGIN TRANSACTION;
        BEGIN TRY
            -- Lógica del procedimiento
            WITH SaldoTransacciones AS (
                SELECT 
                    NumeroCuentaOrigen AS NumeroCuenta,
                    SUM(CASE WHEN TipoTransaccion = 'D' THEN Monto ELSE -Monto END) AS SaldoTransaccion
                FROM Transaccion
                WHERE NumeroCuentaOrigen = @NumeroCuenta
                GROUP BY NumeroCuentaOrigen
            )
            SELECT 
                C.cuenta AS NumeroCuenta,
                C.saldo AS SaldoCuenta,
                ISNULL(ST.SaldoTransaccion, 0) AS SaldoTransaccion
            FROM Cuenta C
            LEFT JOIN SaldoTransacciones ST ON C.cuenta = ST.NumeroCuenta
            WHERE C.cuenta = @NumeroCuenta
            ORDER BY C.cuenta;

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
    END;
END;
