#Class Diagram

<!--
````
@startuml ClassDiagram

Record "n" --> "1" Resident : register
Record "n" --> "1" Visit : register
Record "n" --> "1" Department : ..
Record "n" --> "1" Enterprise : ..
Resident "0..1" --> "1" Department : Live


class Resident
{  
  id : Long
  name : String
  rut : String
  phone : Integer
  email : String
  department_id : Long
  void methods()
}
class Visit {
  id : Long
  name : String
  rut : String
  admitted : String
  void methods()
}

class Record
{
   id : Long
   department_id : Long
   resident_id : Long
   visit_id : Long
   enterprise_id : Long
   kinship : String
   entry_date : date
   departure_date :date
   tipo :char
   comment : String
   
   void methods()
}
class Department {
   id : Long
   number : Integer
   floor : Integer
   block : char
   void methods()
}

class Enterprise{
   id : Long
   name : Date
   void methods()
}
@enduml

-->

