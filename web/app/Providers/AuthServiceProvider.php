<?php

namespace App\Providers;

use Carbon\Carbon;
use Illuminate\Contracts\Foundation\CachesRoutes;
use Illuminate\Foundation\Support\Providers\AuthServiceProvider as ServiceProvider;
use Illuminate\Support\Facades\Gate;
use Laravel\Passport\Passport;
use Mockery\Generator\StringManipulation\Pass\Pass;

class AuthServiceProvider extends ServiceProvider
{
    /**
     * The policy mappings for the application.
     *
     * @var array
     */
    protected $policies = [
         'App\Model' => 'App\Policies\ModelPolicy',
    ];

    /**
     * Register any authentication / authorization services.
     *
     * @return void
     */
    public function boot()
    {
        $this->registerPolicies();

        //The routes of passport.
        Passport::routes();

        //Token expire in 7 dias.
        Passport::personalAccessTokensExpireIn(Carbon::now()->addDays(7));
        Passport::tokensExpireIn(Carbon::now()->addDays(7));
        Passport::refreshTokensExpireIn(Carbon::now()->addDays(30)); // Every 30 days the tokens will being deleted of the database.

    }
}
