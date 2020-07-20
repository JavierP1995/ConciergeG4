<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\RecordResource;
use Illuminate\Http\Request;
use App\Record;
use Illuminate\Support\Facades\DB;
use App\Visit;

class RecordController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $records = Record::all();
        return response([
            'message' => "Retrieved Successfully",
            'records' => RecordResource::collection($records),
        ],200);
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
        $visit = DB::table('visits')->where('rut', $visit_rut)->first();

        if($visit!= null){

            $isAdmitted = $visit->admitted;
            if($isAdmitted == "yes"){

                $department_number = $request->department_number;
                $department = DB::table('departments')->where('number', $department_number)->first();

                if ($department != null){

                    $department_id = $department->id;
                    $resident_id =  DB::table('departments')->where('number', $department_number)->first()->resident_id;
                    $visit_id = $visit->id;

                    $request->request->add(['resident_id' => $resident_id, 'department_id' => $department_id,'visit_id' => $visit_id]);

                    $data = $request->except(['visit_rut', 'department_number']);


                    $record = Record::create($data);

                    return response([
                        'message' => 'Record created successfully',
                        'record' => new RecordResource($record)
                    ], 201);

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
     * Display the visits of a unique department
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($department_id)
    {
        $visits = Record::where('department_id', $department_id)->visit();

        return response([
            'message' => "Retrieved Successfully",
            'visits' => RecordResource::collection($visits),
            ],200);
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
