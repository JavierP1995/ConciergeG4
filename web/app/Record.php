<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Record extends Model
{
    protected $fillable = [
        "type", "kinship", "entryDate", "departureDate", "comment"
    ];
    public function resident(){
        return $this->belongsTo('App\Resident');
    }
    public function visit(){
        return $this->belongsTo('App\Visit');
    }
    public function department(){
        return $this->belongsTo('App\Department');
    }
}
