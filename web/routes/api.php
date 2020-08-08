<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('/register', 'Api\AuthController@register');
Route::post('/login', 'Api\AuthController@login');

Route::get('/logout', 'Api\AuthController@logout')->middleware('auth:api');

// ResidentResource
Route::apiResource('/residents', 'Api\ResidentController')->middleware('auth:api');
Route::get('/departments/{rut}/{option?}', 'Api\ResidentController@show');

// VisitResource
Route::apiResource('/visits', 'Api\VisitController')->middleware('auth:api');
Route::get('/departments/{rut}/{option?}', 'Api\VisitController@show');

// RecordResource
Route::apiResource('/records', 'Api\RecordController');

//DepartmentResource
Route::apiResource('/departments','Api\DepartmentController');
Route::get('/departments/{number}/{option?}', 'Api\DepartmentController@show');
