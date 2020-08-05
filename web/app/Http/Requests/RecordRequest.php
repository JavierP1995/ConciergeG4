<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class RecordRequest extends FormRequest
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

            'kinship' => ['required','max:50'],
            'entryDate' => ['required','date'],
            'departureDate' => ['nullable','date'],
            'comment' => 'nullable|max:255'

        ];
    }

    public function messages()
    {
        return [
            'kinship.required'=> 'Must entered the :attribute',
            'entryDate.required'=> 'Must entered the :attribute',
            'departureDate.required'=> 'Must entered the :attribute',
            'entryDate.date' => 'Format invalid!',
            'departureDate.date' => 'Format invalid!'
        ];
    }
}
