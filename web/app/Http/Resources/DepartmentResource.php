<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class DepartmentResource extends JsonResource
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
        'number' => $this->number,
        'floor' => $this->floor,
        'block' => $this->block,
        'resident' => ResidentResource::collection($this->residents),
        'records' => RecordResource::collection($this->records)
        ];
    }
}
