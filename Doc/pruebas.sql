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

SELECT Medicamento.id_medicamento, Medicamento.nombre, Medicamento.cant_existencia, Medicamento.cant_minima, 
IFNULL((SELECT SUM((precio_actual_medicamento - costo_actual_medicamento) * cantidad)  
FROM Transacciones_Medicamentos
WHERE id_medicamento= Medicamento.id_medicamento
AND fecha > $P{FECHA_INICIAL} 
AND fecha < $P{FECHA_FINAL} ), 0 ) AS 'ganancia'
FROM Medicamento
WHERE Medicamento.nombre LIKE '$P!{FILTRO_NOMBRE}%';

SELECT fecha, cantidad, precio_actual_medicamento, costo_actual_medicamento, 
(precio_actual_medicamento - costo_actual_medicamento) * cantidad AS 'ganancia' 
FROM Transacciones_Medicamentos 
WHERE id_medicamento = 1;


-- VENTAS DE EMPLEADOS 
SELECT Persona.cui, Persona.nombre, Persona.direccion
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona 
INNER JOIN Transacciones_Medicamentos ON Empleado.id_empleado = Transacciones_Medicamentos.id_empleado
WHERE Persona.cui LIKE '$P!{FILTRO_CUI}%' 
AND Persona.nombre LIKE '$P!{FILTRO_NOMBRE_PERSONA}'
GROUP BY Persona.cui;
-- 
SELECT fecha, cantidad, precio_actual_medicamento, costo_actual_medicamento, 
(precio_actual_medicamento - costo_actual_medicamento) * cantidad AS 'ganancia' 
FROM Transacciones_Medicamentos 
INNER JOIN Medicamento ON Medicamento.id_medicamento = Transacciones_Medicamentos.id_medicamento
INNER JOIN Empleado ON Transacciones_Medicamentos.id_empleado = Empleado.id_empleado
WHERE  Empleado.cui_persona= $P{CUI_PERSONA}
AND fecha > $P{FECHA_INICIAL} 
AND fecha < $P{FECHA_FINAL}
AND Medicamento.nombre LIKE '$P!{FILTRO_NOMBRE_MEDICAMENTO}';

SELECT Persona.cui, Persona.nombre, Persona.direccion, Contrato.fecha_inicial, Area.descripcion, Contrato.salario
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona 
INNER JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado 
INNER JOIN Area ON Empleado.id_area = Area.id_area
WHERE Contrato.fecha_inicial > $P{FECHA_INICIAL}
AND Contrato.fecha_inicial < $P{FECHA_FINAL} 


-- Medicos con Paciente asignado 
SELECT Persona.cui, Persona.nombre, Persona.direccion, (SELECT COUNT(id_empleado) FROM Internado_tiene_Empleado 
INNER JOIN Internado ON Internado_tiene_Empleado.id_internado = Internado.id_internado
WHERE Internado.fin IS NULL) AS 'Pacientes asignados'
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona 
INNER JOIN Area ON Empleado.id_area = Area.id_area
WHERE Empleado.id_area = 4
AND (SELECT COUNT(id_empleado) FROM Internado_tiene_Empleado 
INNER JOIN Internado ON Internado_tiene_Empleado.id_internado = Internado.id_internado
WHERE Internado.fin IS NULL) > 0 ;

-- Medicos sin paciente asignado 

SELECT Persona.cui, Persona.nombre, Persona.direccion, (SELECT COUNT(id_empleado) FROM Internado_tiene_Empleado 
INNER JOIN Internado ON Internado_tiene_Empleado.id_internado = Internado.id_internado
WHERE Internado.fin IS NULL 
AND  id_empleado = Empleado.id_empleado) AS 'Pacientes'
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona
INNER JOIN Area ON Empleado.id_area = Area.id_area
WHERE Empleado.id_area = 4
AND (SELECT COUNT(id_empleado) FROM Internado_tiene_Empleado 
INNER JOIN Internado ON Internado_tiene_Empleado.id_internado = Internado.id_internado
WHERE Internado.fin IS NULL 
AND  id_empleado = Empleado.id_empleado) > 0 ;

(SELECT COUNT(id_empleado) FROM Internado_tiene_Empleado 
INNER JOIN Internado ON Internado_tiene_Empleado.id_internado = Internado.id_internado
WHERE Internado.fin IS NULL 
AND  id_empleado = Empleado.id_empleado);


SELECT Persona.cui, Persona.nombre, Contrato.salario
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona
LEFT JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado
LEFT JOIN Pagos_Planilla ON Pagos_Planilla.id_mes = 1
GROUP BY Persona.cui;


-- Seleccionar todos los salarios de los empleados donde el pago del mes no este en la tabla de pagos y el año. 
SELECT Persona.cui, Persona.nombre, Contrato.salario
FROM Persona 
INNER JOIN Empleado ON Persona.cui = Empleado.cui_persona
INNER JOIN Contrato ON Empleado.id_empleado = Contrato.id_empleado
WHERE 1 NOT IN (SELECT pp.id_mes FROM Pagos_Planilla pp 
	INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato
    WHERE c.id_contrato = Contrato.id_contrato)
AND 2019 NOT IN (SELECT pp.anio FROM Pagos_Planilla pp 
	INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato
    WHERE c.id_contrato = Contrato.id_contrato)
AND Contrato.fecha_final IS NULL;


SELECT SUM(Contrato.salario)
FROM Contrato 
INNER JOIN Empleado ON Contrato.id_empleado = Empleado.id_empleado
WHERE 1 NOT IN (SELECT pp.id_mes FROM Pagos_Planilla pp 
	INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato
    WHERE c.id_contrato = Contrato.id_contrato)
AND 2019 NOT IN (SELECT pp.anio FROM Pagos_Planilla pp 
	INNER JOIN Contrato c ON pp.id_contrato = c.id_contrato
    WHERE c.id_contrato = Contrato.id_contrato)
AND Contrato.fecha_final IS NULL;

INSERT INTO Pagos_Planilla (descripcion, monto, id_contrato, id_mes, anio, area, fecha) VALUES (
'Pago correspondiente a ' (SELECT nombre from Meses where id_mes = 1), 
1000, 
19, 
1, 
2019, 
1,
CURDATE());

-- area 
SELECT Empleado.id_area 
FROM Persona, Empleado
WHERE Persona.cui = Empleado.cui_persona 
AND Persona.cui = ?;

-- contrato
SELECT Contrato.id_contrato
FROM Contrato 
INNER JOIN Empleado ON Contrato.id_empleado = Empleado.id_empleado 
WHERE Empleado.cui_persona = ?
AND Contrato.fecha_final IS NULL;

SELECT Contrato.salario 
FROM Contrato 
INNER JOIN Empleado ON Contrato.id_empleado = Empleado.id_empleado 
WHERE Empleado.cui_persona = '123413241234'
AND Contrato.fecha_final IS NULL;

SELECT nombre from Meses where id_mes = 1; 

INSERT INTO Pagos_Planilla (descripcion, monto, id_contrato, id_mes, anio, area, fecha) VALUES (
'Pago de salario Mensual', 
 (SELECT Contrato.salario 
FROM Contrato 
INNER JOIN Empleado  ON Contrato.id_empleado = Empleado.id_empleado 
WHERE Empleado.cui_persona = '123413241234'
AND Contrato.fecha_final IS NULL), 
 (SELECT Contrato.id_contrato 
FROM Contrato 
INNER JOIN Empleado e1 ON Contrato.id_empleado = e1.id_empleado 
WHERE e1.cui_persona = '123413241234' 
AND Contrato.fecha_final IS NULL), 
3, 
2019, 
 (SELECT e.id_area 
FROM Persona p, Empleado e 
WHERE p.cui = e.cui_persona 
AND p.cui = '123413241234'), 
CURDATE());

-- Cuenta, Registro_Monetario, Transacciones_Medicamentos

SELECT Cuenta.monto, Registro_Monetario.monto
FROM Cuenta, Registro_Monetario 
WHERE Cuenta.id_area = 5 
AND Registro_Monetario.id_area = 5;  

SELECT SUM(Registro_Monetario.monto) 
FROM Registro_Monetario 
WHERE Registro_Monetario.id_area = 5;  


-- ingresos

SELECT 
IFNULL((SELECT SUM(precio_al_cliente) 
	FROM Cirugias_Disponibles 
	INNER JOIN Cirugia ON Cirugias_Disponibles.id_tarfia = Cirugia.id_tarifa
	WHERE Cirugia.realizada = true 
	AND fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}), 0)
AS 'Ingresos';


-- egresos 
SELECT 
IFNULL((SELECT SUM(costo_al_hospital) + SUM(tarifa_de_especialista) 
	FROM Cirugias_Disponibles 
	INNER JOIN Cirugia ON Cirugia.id_tarifa = Cirugias_Disponibles.id_tarfia
	WHERE Cirugia.fecha > $P{FECHA_INICIAL}
    AND Cirugia.fecha < $P{FECHA_FINAL}
    AND Cirugia.realizada = true ), 0 ) +
IFNULL((SELECT SUM(monto) 
	FROM Pagos_Planilla 
    WHERE fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}), 0 ) +
IFNULL((SELECT SUM(monto) 
	FROM Costos_Habitacion 
	WHERE fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}), 0 ) +
IFNULL((SELECT SUM(costo_actual_medicamento * cantidad) 
	FROM Transacciones_Medicamentos 
	WHERE fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}), 0 ) 
AS 'Egresos'; 

SELECT fecha, detalle, monto 
FROM Registro_Internados 
WHERE pagado = 1
	AND fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}

SELECT SUM( Cirugias_Disponibles.precio_al_cliente)
	FROM Cirugias_Disponibles 
	INNER JOIN Cirugia ON Cirugias_Disponibles.id_tarfia = Cirugia.id_tarifa
	WHERE Cirugia.realizada = true 
	AND fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}
    
    
SELECT Cirugia.fecha, Cirugias_Disponibles.descripcion, Persona.nombre, Cirugias_Disponibles.precio_al_cliente
	FROM Cirugias_Disponibles 
	INNER JOIN Cirugia ON Cirugias_Disponibles.id_tarfia = Cirugia.id_tarifa
    INNER JOIN Paciente ON Paciente.id_paciente = Cirugia.id_paciente 
    INNER JOIN Persona ON Paciente.cui = Persona.cui
	WHERE Cirugia.realizada = true 
	AND fecha > $P{FECHA_INICIAL}
    AND fecha < $P{FECHA_FINAL}

    


