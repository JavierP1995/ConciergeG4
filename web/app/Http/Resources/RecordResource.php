<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class RecordResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'type' => $this->type,
            'entryDate' => $this->entryDate,
            'departureDate' => $this->departureDate,
            'comment' => $this->comment,
            'resident' => ResidentResource::collection($this->resident),
            'visit' => VisitResource::collection($this->visit),
            'department' => DepartmentResource::collection($this->department),
        ];
    }
}
