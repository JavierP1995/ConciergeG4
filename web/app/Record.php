<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Record extends Model
{
    protected $fillable = [
        "type", "kinship", "entryDate", "departureDate", "comment"
    ];

    /**
     * Scope a query to only include records of a unique department
     *
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function scopeByDepartment($query, $id_department)
    {
        return $query->where('department_id', '%'.$id_department.'%');
    }

    public function resident()
    {
        return $this->belongsTo('App\Resident');
    }

    public function visit()
    {
        return $this->belongsTo('App\Visit');
    }

    public function department()
    {
        return $this->belongsTo('App\Department');
    }

}
