package com.example.android

import com.example.android.model.DepartmentModel

data class ListDepartments(var loading: Boolean, var departments: List<DepartmentModel>)