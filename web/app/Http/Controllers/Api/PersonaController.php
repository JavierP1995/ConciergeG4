<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\PersonaResource;
use App\Persona;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class PersonaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $personas = Persona::all();
        return response([
            'message' => "Retrieved Successfully",
            'personas' => PersonaResource::collection($personas),
        ]);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();
        $persona = Persona::create($data);

        return response([
            'message' => 'Created Successfully',
            'persona' => new PersonaResource($persona),
        ],  201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function show(Persona $persona)
    {
        return response([
            'message' => 'Retrieved Succesfully',
            'persona' => new PersonaResource($persona)
        ], 200);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Persona $persona)
    {
        $persona->update($request->all());

        return response([
            'message' => 'Updated Successfully',
            'persona' => new PersonaResourse($persona),
        ], 200);

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Persona  $persona
     * @return \Illuminate\Http\Response
     */
    public function destroy(Persona $persona)
    {
        $persona->delete();

        return response([
           'message' => 'Persona Deleted',
        ]);

    }
}
