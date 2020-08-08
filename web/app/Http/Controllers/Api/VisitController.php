<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\VisitRequest;
use App\Http\Resources\RecordResource;
use App\Http\Resources\ResidentResource;
use App\Http\Resources\VisitResource;
use App\Record;
use App\Resident;
use App\Visit;
use Illuminate\Database\Eloquent\Collection;
use Symfony\Component\HttpKernel\Exception\HttpException;

class VisitController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        try{
            $visits = Visit::all();
            $data = response(VisitResource::collection($visits));
        }
        catch(HttpException $error){
            $data = $error->getMessage();
        }
        return $data;
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
     * @param String $rut
     * @param String $option
     * @return \Illuminate\Http\Response
     */
    public function show($rut, $option = null)
    {
        if($option == null)
        {
            $visits = Visit::all()->where('rut', 'like', $rut. '%');
            return response(
                VisitResource::collection($visits)
            );
        }
        else {
            $visit = Visit::all()->where('rut', $rut)->first();
            $records = Record::all()->where('visit_id', $visit->id);
            if ($option == 'records')
            {
                return response(
                    RecordResource::collection($records),
                    200
                );
            } elseif ($option == 'residents')
            {
                $residents = new Collection();
                foreach ($records as $record) {
                    $resident = Resident::all()->where('id', $record->resident_id)->first();
                    if (!$residents->contains($resident)) {
                        $residents->add($resident);
                    }
                }
                return response(
                    ResidentResource::collection($residents),
                    200
                );
            }
            else{
                return response('message',  'failed route');
            }
        }
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
