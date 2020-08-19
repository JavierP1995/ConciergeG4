<?php

namespace App\Http\Controllers\Api;

use App\Department;
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
     * Display visits filtered by an specified parameter
     *
     * @param string $search parameter to search
     * @param string $option type of parameter
     * @return \Illuminate\Http\Response
     */
    public function show($search, $option = null)
    {
        if ($option == null)
        {
            $visits = Visit::all()->where('rut', $search);
        }
        elseif ($option == "department")
        {
            $department = Department::all()->where('number', $search)->first();
            $records = Record::all()->where('department_id', $department->id);
            $visits = new Collection();
            foreach ($records as $record)
            {
                $visit = Visit::all()->where('id', $record->visit_id);
                if (!$visits.contains($visit))
                {
                    $visits->add($visit);
                }
            }
        }
        elseif ($option == "resident")
        {
            $resident = Resident::all()->where('rut', $search)->first();
            $records = Record::all()->where('resident_id', $resident->id);
            $visits = new Collection();
            foreach ($records as $record)
            {
                $visit = Visit::all()->where('id', $record->visit_id);
                if (!$visits->contains($visit))
                {
                    $visits->add($visit);
                }
            }
        }else{
            return response(
                ["message", "invalid route"]
            );
        }
        return response(
            ResidentResource::collection($visits)
        );
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
