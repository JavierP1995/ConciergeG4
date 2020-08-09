<?php

namespace App\Http\Controllers\Api;

use App\Department;
use App\Http\Controllers\Controller;
use App\Http\Requests\DepartmentRequest;
use App\Http\Resources\DepartmentResource;
use App\Http\Resources\RecordResource;
use App\Http\Resources\ResidentResource;
use App\Http\Resources\VisitResource;
use App\Record;
use App\Resident;
use App\Visit;
use Facade\FlareClient\Http\Exceptions\BadResponse;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\Exceptions\HttpResponseException;
use Illuminate\Http\Request;
use Symfony\Component\HttpKernel\Exception\HttpException;


class DepartmentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        try{
            $departments = Department::all();
            $data = response(DepartmentResource::collection($departments));
        }
        catch(HttpException $error){
            $data = $error->getMessage();
        }
        return $data;
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
            //'message' => 'Created Susccesfully',
            'department' => new DepartmentResource($department)
        ], 201);

    }

    /**
     * Display visits from an specific department
     *
     * @param  string $search
     * @param string $option
     * @return \Illuminate\Http\Response
     */
    public function show($search, $option = null)
    {
        if($option == null) {
            $departments = Department::all()->where('number', $search);
        }
        elseif($option == 'resident')
        {
            $resident = Resident::all()->where('rut', $search)->first();
            $departments = Department::all()->where('id', $resident->id);
        }
        elseif($option == 'visit')
        {
            $visit = Visit::all()->where('rut', $search)->first();
            $records = Record::all()->where('visit_id', $visit->id);
            $departments = new Collection();
            foreach ($records as $record)
            {
                $department = Department::all()->where('id', $record->department_id);
                if (!$departments.contains($department))
                {
                    $departments->add($department);
                }
            }
        }
        else
        {
            return response(
                ["message", "invalid route"]
            );
        }
        return response(
            DepartmentResource::collection($departments)
        );
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
     **
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
