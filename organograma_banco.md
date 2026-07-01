o Banco esta dessa forma

status
-------
idStatus (PK)
nome

consulta
---------
idConsulta
...
idStatus (FK -> status.idStatus)

chamada_paciente
----------------
idChamada
...
idStatus (FK -> status.idStatus)