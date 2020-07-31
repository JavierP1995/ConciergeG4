package com.example.android

import com.example.android.model.DepartmentModel


data class ListDepartments(var departments: Collection<DepartmentModel>,var loading: Boolean)