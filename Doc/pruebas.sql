SELECT Habitacion.id_habitacion, IF((SELECT COUNT(Internado.id_habitacion) FROM Internado 
WHERE Internado.id_habitacion = Habitacion.id_habitacion AND Internado.fin IS NULL ) > 0 , 'Ocupada', '--') AS 'Ocupada',
Persona.nombre AS 'Paciente',  esta_habilitada, precio_de_mantenimiento AS 'Costo de Mantenimiento'
FROM Habitacion
LEFT JOIN Internado ON Habitacion.id_habitacion = Internado.id_habitacion
LEFT JOIN Paciente ON Internado.id_paciente = Paciente.id_paciente
LEFT JOIN Persona ON Paciente.cui = Persona.cui
WHERE Internado.fin is null; 

UPDATE Habitacion SET precio_de_mantenimiento = 10 ;

SELECT COUNT(Internado.id_habitacion) FROM Internado 
INNER JOIN Habitacion ON  Internado.id_habitacion = Habitacion.id_habitacion 
WHERE Internado.fin IS NULL 
AND Habitacion.id_habitacion = 1