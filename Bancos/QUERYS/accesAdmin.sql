
alter procedure accesAdmin
(
@UserAdmin varchar(25),
@Pin varchar(10)
)
as
begin
	DECLARE @Intentos INT = 0;
    DECLARE @FechaHora DATETIME = GETDATE();
    DECLARE @Dead INT = 1205;
	while @Intentos < 3
	begin
		declare @ROL varchar(25)
		SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
		begin transaction 
		begin try
			select @ROL = rol
			from Usuario where nombreUsuario = @UserAdmin and pin = @Pin
				if(@ROL = 'ADMIN')
				begin
					select nombreUsuario as NombreAdmin, rol, pin from Usuario where nombreUsuario = @UserAdmin and pin = @Pin
				end
				else
				begin
					RAISERROR('El Usuario ingresado no es Administrador', 16, 1);
					RETURN;
				end
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
