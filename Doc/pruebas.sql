SELECT persona.cui, persona.nombre, IFNULL(Internado.inicio, '--') AS 'Ingreso', IFNULL(Internado.fin, '--') AS 'Egreso', 
IFNULL(( 
(SELECT SUM(precio_de_consulta) FROM Consulta 	WHERE Consulta.pagado = false AND Consulta.id_paciente = paciente.id_paciente) 
) 	+ (SELECT SUM(precio_actual_medicamento) FROM Transacciones_Medicamentos WHERE Transacciones_Medicamentos.cui_paciente = persona.cui AND Transacciones_Medicamentos.pagado = false) +
	(SELECT SUM(Cirugias_Disponibles.precio_al_cliente) FROM Cirugias_Disponibles INNER JOIN Cirugia  ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia WHERE Cirugia.id_paciente = paciente.id_paciente AND Cirugia.pagada = false) + 
    (SELECT SUM(monto) FROM Registro_Internados WHERE id_paciente = paciente.id_paciente AND pagado = false)  , 0 )  AS 'Cuenta'
FROM Persona persona
INNER JOIN Paciente paciente ON persona.cui = paciente.cui  
LEFT JOIN Internado ON paciente.id_paciente = Internado.id_paciente  
LEFT JOIN Registro_Internados ON paciente.id_paciente = Registro_Internados.id_paciente
GROUP BY persona.cui;  


    







