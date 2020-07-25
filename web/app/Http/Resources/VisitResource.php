<?php

namespace App\Http\Resources;

use App\Http\Requests\RecordRequest;
use Illuminate\Http\Resources\Json\JsonResource;

class VisitResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return[
            'rut' => $this->rut,
            'name' => $this->name,
            'admitted' => $this->admitted
        ];
    }
}
