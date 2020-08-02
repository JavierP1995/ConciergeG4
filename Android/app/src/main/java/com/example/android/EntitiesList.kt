package com.example.android

import com.example.android.model.DepartmentModel
import com.example.android.model.RecordModel
import com.example.android.model.ResidentModel
import com.example.android.model.VisitModel


data class ListDepartments(var departments: Collection<DepartmentModel>,var loading: Boolean)

data class ListRecords(var records : Collection<RecordModel>, var loading: Boolean)

data class ListVisits(var visits: Collection<VisitModel>, var loading: Boolean)

data class ListResidents(var residents: Collection<ResidentModel>, var loading: Boolean)