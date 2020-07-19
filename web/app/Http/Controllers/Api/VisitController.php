<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\VisitRequest;
use App\Http\Resources\VisitResource;
use App\Visit;

class VisitController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $visits = Visit::all();

        return response([
            'message' => "Retrieved Successfully",
            'visits' => VisitResource::collection($visits)
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     *
     * @param VisitRequest $request
     * @return \Illuminate\Http\Response
     */
    public function store(VisitRequest $request)
    {
        $data = $request->all();

        $visit = Visit::create($data);

        return response([
            'message' => 'Created Successfully',
            'visit' => new VisitResource($visit)
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param Visit $visit
     * @return \Illuminate\Http\Response
     */
    public function show(Visit $visit)
    {
        return response([
            'message' => 'Retrieved Successfully',
            'visit' => new VisitResource($visit),
        ], 200);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param VisitRequest $request
     * @param Visit $visit
     * @return \Illuminate\Http\Response
     */
    public function update(VisitRequest $request, Visit $visit)
    {
        $visit->update($request->all());

        return response([
            'message' => 'Updated Successfully',
            'visit' => new VisitResource($visit),
        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param Visit $visit
     * @return \Illuminate\Http\Response
     * @throws \Exception
     */
    public function destroy(Visit $visit)
    {
        $visit->delete();

        return response([
            'message' => 'Visit Deleted Successfully'
        ]);
    }
}
