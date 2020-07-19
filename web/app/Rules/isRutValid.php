<?php

namespace App\Rules;

use Illuminate\Contracts\Validation\Rule;

class isRutValid implements Rule
{
    /**
     * Create a new rule instance.
     *
     * @return void
     */
    public function __construct()
    {
        //
    }

    /**
     * Determine if the validation rule passes.
     *
     * @param  string  $attribute
     * @param  mixed  $value
     * @return bool
     */
    public function passes($attribute, $value)
    {
        return self::rutValidation($value);
    }

    /**
     * Get the validation error message.
     *
     * @return string
     */
    public function message()
    {
        return 'The entered RUT is not valid!';
    }

    /**
     * Validate the entered RUT
     *
     * @param $rut
     * @return bool
     */
    static function rutValidation ( $rut )
    {

        if ($rut == null){
            return false;
        }

        if ( !preg_match("/^[0-9]+-[0-9kK]{1}/",$rut)){
            return false;
        }
        $rut = explode('-', $rut);

        return strtolower($rut[1]) == isRutValid::verifyingDigit($rut[0]);
    }

    /**
     * Get the verify digit of RUT.
     *
     * @param $T
     * @return int|string
     */
    static function verifyingDigit ( $T )
    {
        $M=0;$S=1;
        for(;$T;$T=floor($T/10))
            $S=($S+$T%10*(9-$M++%6))%11;
        return $S?$S-1:'k';
    }

}
