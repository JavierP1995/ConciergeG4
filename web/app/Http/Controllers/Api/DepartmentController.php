<?php

namespace App\Http\Controllers\Api;

use App\Department;
use App\Http\Controllers\Controller;
use App\Http\Requests\DepartmentRequest;
use App\Http\Resources\DepartmentResource;
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
     * @param  int $number
     * @param string $option
     * @return \Illuminate\Http\Response
     */
    public function show($number, $option = null)
    {
        if($option == "visits")
        {
            $department = Department::all()->where('number', $number)->first();
            $records = Record::all()->where('department_id', $department->id);
            $visits = new Collection();
            foreach ($records as $record){
                $visit = Visit::all()->where('id', $record->visit_id)->first();
                if(!$visits->contains($visit)){
                    $visits->add($visit);
                }
            }
            return response(
                VisitResource::collection($visits), 200);
        }
        elseif($option == "residents")
        {
            $department = Department::all()->where('number', $number)->first();
            $residents = Resident::all()->where('department_id', $department->id);
            return response(
                ResidentResource::collection($residents), 200);
        }
        else
        {
            $department = Department::all()->where('number', $number)->first();
            return response(
                $department
            );
        }
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
