<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\RecordResource;
use Illuminate\Http\Request;
use App\Record;
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
        //
    }

    /**
     * Display the visits of a unique department
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($department_id)
    {
        $visits = Record::where('department_id', $department_id)->visits;
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
