SELECT id_area FROM Credenciales WHERE id_empleado = 19; 

-- consulta de pacientes internados 
SELECT Persona.cui as 'CUI', Persona.nombre AS 'Nombre Completo', Internado.inicio AS 'Fecha de Ingreso', Internado.id_habitacion AS 'Habitacion'
FROM Persona
INNER JOIN Paciente ON Persona.cui = Paciente.cui
INNER JOIN Internado ON Paciente.id_paciente = Internado.id_paciente
INNER JOIN Internado_tiene_Empleado ON Internado_tiene_Empleado.id_internado= Internado.id_internado 
INNER JOIN Empleado ON Empleado.id_empleado = Internado_tiene_Empleado.id_empleado 
WHERE Empleado.id_empleado = 20
AND Internado.fin IS NULL;


-- Consultar Dias internado 
SELECT TIMESTAMPDIFF(DAY, Internado.inicio, CURDATE()) as 'Dias' 
FROM Internado
INNER JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente
WHERE Paciente.cui = '4455';  
;

SELECT monto FROM Constantes WHERE id_constante = 2;

INSERT INTO Cuenta (id_paciente, detalle, monto, pagado, fecha, id_area) VALUES ((SELECT id_paciente FROM Paciente WHERE cui = '4455'), 
'Instancia en el hospital de 3 dias', (SELECT monto FROM Constantes WHERE id_constante = 2) * 3, false, CURDATE(), 5 );

-- salida del paciente x de la habitacion 
UPDATE Habitacion SET esta_ocupada = 0 
WHERE Habitacion.id_habitacion = 
	(SELECT Habitacion.id_habitacion 
    FROM Habitacion 
    INNER JOIN Internado ON Habitacion.id_habitacion = Internado.id_habitacion 
    INNER JOIN Paciente ON  Internado.id_paciente = Paciente.id_paciente 
    WHERE Paciente.cui = '4455');
    
UPDATE Cuenta SET pagado = true WHERE
Paciente.id_paciente = Cuenta.id_paciente 
AND Persona.cui = Paciente.cui 
AND Persona.cui = '4455'; 

