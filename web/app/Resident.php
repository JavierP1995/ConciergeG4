<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Resident extends Model
{
   protected $fillable = [
       "rut", "name", "phone", "email", "department_id"
   ];

   protected $table = 'residents';

    public function records(){
        return $this->hasMany('App\Record');
    }
    public function department(){
        return $this->belongsTo('App\Department');
    }
}
