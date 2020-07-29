#Class Diagram

<!--
````
@startuml ClassDiagram

RecordModel "n" --> "1" ResidentModel : register
RecordModel "n" --> "1" VisitModel : register
RecordModel "n" --> "1" DepartmentModel : ..
ResidentModel "1..n" --> "1" DepartmentModel : Live


class ResidentModel
{  
  rut : String
  name : String
  email : String
  phone : Integer
  department : Department
  void methods()
}
class VisitModel {
  id : Long
  name : String
  rut : String
  admitted : String
  void methods()
}

class RecordModel
{
   kinship : String
   entry_date : date
   departure_date :date
   comment : String
   resident:String
   visit: String
   department: Integer
   void methods()
}
class DepartmentModel {
   number : Integer
   floor : Integer
   block : char
   void methods()
}
@enduml

-->

