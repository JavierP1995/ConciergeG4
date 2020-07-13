<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AuthController extends Controller
 {
    public function register(Request $request)
    {
        $data = $request->all();

        //The rules of validation
        $validator = Validator::make($data,[
            'name'=>'required|min:3|max:255',
            'email'=>'email:rfc,dns|required|unique:users',
            'password'=>'required|min:8|max:255|confirmed'
        ]);

        //If validation fails, returned 412
        //412 Precondition Failed:
        //One or more conditions in the request header fields evaluated to false
        if($validator->fails())
        {
            return response ([
                'error' => 'Validation Error',
                'message' => $validator->errors()
            ],412);
        }

        //Apply bcrypt to password (very secure)
        /** @noinspection PhpUndefinedFieldInspection */
        $data['password'] = bcrypt($request->password);

        // Insert the user into the database.
        $user = User::create($data);

        $authToken = $user -> createToken('authToken');


        //Return the token to the user
        return response ([
            'user' => $user,
            'token'=> $authToken->accessToken,
            'token_expires_at' => $authToken->token->expires_at,
        ]);
    }

    /**
     * Do the login, returning the access token.
     * @param Request $request
     * @return \Illuminate\Http\Response
     */
    public function login(Request $request) {

        //All the data from request
        $data = $request->all();

        $validator = Validator::make($data, [
            'email' => 'email:rfc,dns|required',
            'password' => 'required'
        ]);

        //If validation fails, returned 412
        //412 Precondition Failed:
        //One or more conditions in the request header fields evaluated to false
        if($validator -> fails()) {
            return response([
                'error' => 'Validation Error',
                'message' => $validator->errors(),
            ], 412);
        }

        //Fail: 401 Unauthorized: Client failed to authenticate with server
        if(!auth()->attempt($data)) {
            return response([
                'error' => 'Invalid Credentials',
                'message' => 'Unauthorized, wrong email or password',
            ], 401);
        }

        $authToken = auth()->user()->createToken('authToken');

        return response([
            'user' => auth()->user(),
            'token' => $authToken->accessToken,
            'token_expires_at' => $authToken->token->expires_at,
        ]);
    }

    /**
     * Logout user.
     * @param Request $request
     * @return \Illuminate\Http\Response
     */
    public function logout(Request $request)
    {
        $request->user()->token()->revoke();

        return response([
            'message' => 'Successfully logged out'
        ]);
    }
}
