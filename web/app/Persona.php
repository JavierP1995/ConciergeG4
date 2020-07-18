<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Persona extends Model
{
   protected $fillable = [
       "rut", "name", "phone", "email"
   ];
    public function records(){
        return $this->hasMany('App\Record');
    }
}
