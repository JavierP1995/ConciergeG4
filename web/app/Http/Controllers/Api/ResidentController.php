<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\ResidentRequest;
use App\Http\Resources\ResidentResource;
use App\Resident;

class ResidentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $residents = Resident::all();
        return response([
            'message' => "Retrieved Successfully",
            'residents' => ResidentResource::collection($residents),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  $request
     * @return \Illuminate\Http\Response
     */
    public function store(ResidentRequest $request)
    {
       $data = $request->all();

       $resident = Resident::create($data);

       return response([
            'message' => 'Created Successfully',
            'resident' => new ResidentResource($resident),
        ],  201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Resident  $resident
     * @return \Illuminate\Http\Response
     */
    public function show(Resident $resident)
    {
        return response([
            'message' => 'Retrieved Succesfully',
            'resident' => new ResidentResource($resident)
        ], 200);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Resident  $resident
     * @return \Illuminate\Http\Response
     */
    public function update(ResidentRequest $request, Resident $resident)
    {
        $resident->update($request->all());

        return response([
            'message' => 'Updated Successfully',
            'resident' => new ResidentResource($resident),
        ], 200);

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Resident  $resident
     * @return \Illuminate\Http\Response
     */
    public function destroy(Resident $resident)
    {
        $resident->delete();
        return response([
           'message' => 'Resident Deleted',
        ]);

    }
}
