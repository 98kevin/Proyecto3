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

