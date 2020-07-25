<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class DepartmentRequest extends FormRequest
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

            'number' => ['required','max:5','unique:departments'],
            'floor' => 'required',
            'block' => 'required|max:1',

        ];
    }

    public function messages()
    {
        return [
          'number.required' => 'The :attribute  it is required!',
          'number.max' => ':attribute invalid',
          'floor.required' => 'The :attribute  it is required!',
          'floor.max' => ':attribute invalid',
          'block.required' => 'The :attribute  it is required!',
          'block.max' => ':attribute invalid',

        ];
    }
}
