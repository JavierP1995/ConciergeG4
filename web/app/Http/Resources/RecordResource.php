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
        if($this->departureDate == null)
        {
            $departureDate = "not yet";
        }else
        {
            $departureDate = $this->departureDate->format('d-m-Y H:i');
        }
        if($this->visit == null)
        {
            $visit = "not a visit";
        }else
        {
            $visit = $this->visit->name;
        }
        return [
            'kinship' => $this->kinship,
            'entryDate' => $this->entryDate->format('d-m-Y H:i'),
            'departureDate' => $departureDate,
            'comment' => $this->comment,
            'resident' => $this->resident->name,
            'visit' => $visit,
            'department' => $this->department->number,
            'id' => $this->id
        ];
    }
}
