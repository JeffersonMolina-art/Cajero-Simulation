
alter procedure inicioSeccion(@Cuenta varchar(25), @PIN varchar(10))
as
begin
	DECLARE @Intentos INT = 0;
    DECLARE @Dead INT = 1205;
	while @Intentos < 3
	begin
		SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
		begin transaction 
		begin try
			select  u.nombreUsuario, u.rol, c.cuenta, c.saldo, u.telefono, u.pin From Usuario as u
			join Cuenta as c on
			c.idUsuario = u.idUsuario
			where Cuenta = @Cuenta AND pin = @PIN
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

