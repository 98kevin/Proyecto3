SELECT id_tarfia, descripcion AS 'Cirugia', costo_al_hospital AS 'Costo', tarifa_de_especialista AS 'Precio', 
precio_al_cliente AS 'Tarifa de especialista'
FROM Persona 
INNER JOIN Paciente ON Persona.cui=Paciente.cui