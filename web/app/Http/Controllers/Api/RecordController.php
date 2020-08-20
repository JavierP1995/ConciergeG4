<?php

namespace App\Http\Controllers\Api;

use App\Department;
use App\Http\Controllers\Controller;
use App\Http\Resources\RecordResource;
use App\Http\Resources\VisitResource;
use App\Resident;
use App\Visit;
use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Record;
use Illuminate\Support\Facades\DB;
use Symfony\Component\HttpKernel\Exception\HttpException;


class RecordController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        try{
            $records = Record::all();
            $data = response(RecordResource::collection($records));
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
    public function store(Request $request)
    {
        $visit_rut = $request->visit_rut;
        $visit = Visit::all()->where('rut', $visit_rut)->first();

        // We convert the date formats



        // If the visitant is registered in the database.
        if($visit!= null){
            $isAdmitted = $visit->admitted;
            // If the visitant admitted attribute it's equals to yes.
            if($isAdmitted == "yes"){

                $department_number = $request->department_number;
                //$department = DB::table('departments')->where('number', $department_number)->first();
                $department = Department::all()->where('number', $department_number)->first();

                // If the department number exists in the database.
                if ($department != null){

                    $department_id = $department->id;

                    /*$resident_id = DB::table('residents')
                                ->where('name', '=' ,$request->resident_name) // Clausula and
                                ->where('department_id', '=' ,$department_id)->value('id');*/

                    error_log($request->resident_name);

                    $resident = Resident::all()->where('name', $request->resident_name)
                        ->where('department_id', $department_id)->first();

                    if ($resident != null){

                        $resident_id = $resident->id;

                        $visit_id = $visit->id;

                        $request->request->add(['resident_id' => $resident_id, 'department_id' => $department_id,'visit_id' => $visit_id]);

                        $request->request->add(['entryDate' => Carbon::now()]);

                        $data = $request->except(['visit_rut', 'department_number', 'resident_name']);

                        $record = Record::create($data); // Create the record

                        error_log("Almost insert !");

                        return response([
                             'message' => 'Record created successfully',
                             'record' => new RecordResource($record)
                        ], 201);

                    }else{

                        return response([
                            'message' => "The provided visit name it's not exists !"
                        ]);

                    }


                }else{
                    return response([
                        'message' => "The provided number it's not exists !"
                    ]);
                }

            }else{
                return response([
                    'message' => "The visitant isn't admitted"
                ]);
            }

        }else{
            return response([
                'message' => "The visitant is not registered !"
            ]);
        }

    }

    /**
     * Display records filtered by an specified parameter
     *
     * @param string $search parameter to search
     * @param string $option type of parameter
     * @return \Illuminate\Http\Response
     */
    public function show($search, $option)
    {
        if ($option == 'resident')
        {
            $resident = Resident::all()->where('rut', $search)->first();
            $records = Record::all()->where('resident_id', $resident->id);
        }
        elseif ($option == "department")
        {
            $department = Department::all()->where('number', $search)->first();
            $records = Record::all()->where('department_id', $department->id);
        }
        elseif ($option == "visit")
        {
            $visit = Visit::all()->where('rut', $search)->first();
            $records = Record::all()->where('resident_id', $visit->id);

        }else{
            return response(
                ["message", "invalid route"]
            );
        }
        return response(
            RecordResource::collection($records)
        );
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
