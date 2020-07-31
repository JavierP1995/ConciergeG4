<?php

namespace App\Http\Controllers\Api;

use App\Department;
use App\Http\Controllers\Controller;
use App\Http\Requests\DepartmentRequest;
use App\Http\Resources\DepartmentResource;
use App\Http\Resources\VisitResource;
use App\Record;
use App\Visit;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\Request;

class DepartmentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $departaments = Department::all();

        return response([
            'message' => "Retrieved Successfully",
            'departments' =>DepartmentResource::collection($departaments)
        ],200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(DepartmentRequest $request)
    {
        $info = $request->all();
        $department = Department::create($info);

        return response([
            'message' => 'Created Susccesfully',
            'department' => new DepartmentResource($department)
        ], 201);

    }

    /**
     * Display visits from an specific department
     *
     * @param  int $number
     * @return \Illuminate\Http\Response
     */
    public function show($number)
    {
        $department = Department::all()->where('number', $number)->first();
        $records = Record::all()->where('department_id', $department->id);
        $visits = new Collection();
        foreach ($records as $record){
            $visits->add(Visit::all()->where('id', $record->visit_id)->first());
        }

        return response([
            'message' => 'Retrieved Succesfully',
            'visits' => VisitResource::collection($visits)
        ], 200);

    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Department  $department
     * @return \Illuminate\Http\Response
     */
    public function update(DepartmentRequest $request, Department $department)
    {
        $department->update($request->all());

        return response([
            'message' => 'Updated Successfully',
            'department' => new DepartmentResource($department),
        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Department  $department
     * @return \Illuminate\Http\Response
     */
    public function destroy(Department $department)
    {
        $department->delete();
        return response([
           'message' => 'Department Deleted',
        ]);
    }
}
