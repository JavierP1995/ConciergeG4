<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Department extends Model
{
    protected $fillable =
        [
            'number','floor','block','resident_id'
        ];

    protected $table = 'departments';

    public function residents(){
        return $this->hasMany('App\Resident');
    }
    public function records(){
        return $this->hasMany('App\Record');
    }
}
