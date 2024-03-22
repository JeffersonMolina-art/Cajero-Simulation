
create procedure HistorialUser(@Cuenta Varchar(25))
as
begin
	DECLARE @Intentos INT = 0;
    DECLARE @FechaHora DATETIME = GETDATE();
    DECLARE @Dead INT = 1205;
	while @Intentos < 3
	begin
		SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
		begin transaction 
		begin try
			select TipoTransaccion, monto, fechahora from Transaccion where NumeroCuentaOrigen = @Cuenta
			commit
			break
		end try
		begin catch
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
