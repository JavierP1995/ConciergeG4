<?php

namespace App\Http\Requests;

use App\Rules\isRutValid;
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
            'rut' => ['required','max:50', new isRutValid()],
            'name' => 'required|max:255',
            'phone' => 'max:12',
            'email' => 'required|email:rfc,dns|max:255'
        ];
    }

    /**
     * Return the messages when throws some error
     *
     * @return array|string[]
     */
    public function messages()
    {
        return [
            'rut.required' => 'The :attribute it is required',
            'name.required' => 'Must entered the :attribute',
            'email.required' => 'Must entered the :attribute',
            'phone.max' => 'The :attribute exceeds the maximum'
            ];
    }

}
