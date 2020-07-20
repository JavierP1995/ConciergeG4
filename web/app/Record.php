<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Record extends Model
{
    protected $fillable = [
        "type", "kinship", "entryDate", "departureDate", "comment"
    ];
    protected $table = 'records';

    public function resident()
    {
        return $this->belongsTo('App\Resident','resident_id');
    }

    public function visit()
    {
        return $this->belongsTo('App\Visit','visit_id');
    }

    public function department()
    {
        return $this->belongsTo('App\Department', 'department_id');
    }

}
