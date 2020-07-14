<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class PersonaRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'rut' => 'requiered|max:50',
            'name' => 'requiered|max:255',
            'phone' => 'max:50',
            'email' => 'requiered|email:rfc,dns|max:255'
        ];
    }

}
