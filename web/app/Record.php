<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Record extends Model
{
    protected $fillable = [
        "visit_rut", "department_number", "type", "kinship", "entryDate", "departureDate", "comment", 'resident_id', 'visit_id', 'department_id'
    ];

    protected $hidden = [
        'id'
    ];

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
