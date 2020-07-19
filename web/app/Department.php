<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Department extends Model
{
    protected $fillable =
        [
            'id','numero','piso','bloque'
        ];


    public function resident(){
        return $this->hasMany('App\Resident');
    }
}
